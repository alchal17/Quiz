package com.example.quiz.ui.pages

import android.graphics.BitmapFactory
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts.GetContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.chargemap.compose.numberpicker.NumberPicker
import com.example.quiz.R
import com.example.quiz.api.ApiResponse
import com.example.quiz.models.database_representation.QuizQuestionOption
import com.example.quiz.models.request_representation.Base64QuizQuestion
import com.example.quiz.ui.base64ToBitmap
import com.example.quiz.ui.bitmapToBase64
import com.example.quiz.viewmodels.QuizQuestionOptionViewModel
import com.example.quiz.viewmodels.QuizQuestionViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import java.io.InputStream


@Composable
fun Base64QuizQuestionManaging(
    quizId: Int,
    questionOrderNumber: Int,
    initialQuestionId: Int?,
    navController: NavController
) {
    val context = LocalContext.current

    val quizQuestionViewModel = koinViewModel<QuizQuestionViewModel>()
    val optionsViewModel = koinViewModel<QuizQuestionOptionViewModel>()

    var topText by remember { mutableStateOf("") }

    var options = remember { mutableStateListOf<QuizQuestionOption>() }

    var initialQuestion by remember { mutableStateOf<Base64QuizQuestion?>(null) }
    var newQuestion by remember {
        mutableStateOf(
            Base64QuizQuestion(
                id = null,
                quizId = quizId,
                text = "",
                base64Image = null,
                multipleChoices = false,
                secondsToAnswer = 10,
                orderNumber = questionOrderNumber
            )
        )
    }

    val imagePickerLauncher =
        rememberLauncherForActivityResult(GetContent()) { uri: Uri? ->
            uri?.let {
                val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
                val bitmap = BitmapFactory.decodeStream(inputStream)
                if (initialQuestionId == null) {
                    newQuestion = newQuestion.copy(base64Image = bitmapToBase64(bitmap))
                } else {
                    initialQuestion =
                        initialQuestion?.copy(base64Image = bitmapToBase64(bitmap))
                }
            }
        }

    val coroutineScope = rememberCoroutineScope()


    LaunchedEffect(Unit) {
        if (initialQuestionId != null) {
            initialQuestion =
                when (val response =
                    quizQuestionViewModel.getBase64QuestionById(initialQuestionId)) {
                    is ApiResponse.Error -> {
                        Toast.makeText(context, response.message, Toast.LENGTH_SHORT).show()
                        null
                    }

                    is ApiResponse.Success -> {
                        topText = response.data.text
                        response.data
                    }
                }
            options.addAll(
                when (val result = optionsViewModel.findAllByQuizQuestionId(initialQuestionId)) {
                    is ApiResponse.Error -> {
                        Toast.makeText(context, result.message, Toast.LENGTH_SHORT).show()
                        emptyList<QuizQuestionOption>().toMutableStateList()
                    }

                    is ApiResponse.Success -> result.data.toMutableStateList()
                }
            )
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(if (initialQuestionId == null) "New question" else "Update $topText")
                TextField(
                    value = if (initialQuestionId == null) newQuestion.text else initialQuestion?.text
                        ?: "",
                    onValueChange = {
                        if (initialQuestionId == null) {
                            newQuestion = newQuestion.copy(text = it)
                        } else {
                            initialQuestion = initialQuestion?.copy(text = it)
                        }
                    },
                    label = { Text("Question text") }
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Seconds to answer:")
                    NumberPicker(
                        range = 5..30,
                        value = if (initialQuestionId == null) newQuestion.secondsToAnswer
                        else initialQuestion?.secondsToAnswer
                            ?: 10,
                        onValueChange = { newTime ->
                            if (initialQuestionId == null) {
                                newQuestion = newQuestion.copy(secondsToAnswer = newTime)
                            } else {
                                initialQuestion = initialQuestion?.copy(secondsToAnswer = newTime)
                            }
                        }
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Allow multiple correct options:")
                    RadioButton(
                        selected = if (initialQuestionId == null) newQuestion.multipleChoices
                        else initialQuestion?.multipleChoices == true,
                        onClick = {
                            if (initialQuestionId == null) {
                                newQuestion =
                                    newQuestion.copy(multipleChoices = !newQuestion.multipleChoices)
                            } else {
                                initialQuestion = initialQuestion?.copy(
                                    multipleChoices = !(initialQuestion?.multipleChoices ?: false)
                                )
                            }
                        }
                    )
                }
                if (initialQuestionId == null && newQuestion.base64Image == null ||
                    initialQuestionId != null && initialQuestion?.base64Image == null
                ) {
                    Button(onClick = { imagePickerLauncher.launch("image/*") }) {
                        Row {
                            Icon(
                                painter = painterResource(R.drawable.image_logo),
                                "Pick an image"
                            )
                            Text("Add an image", fontFamily = FontFamily(Font(R.font.oswald_light)))
                        }
                    }
                }
                listOf(initialQuestion?.base64Image, newQuestion.base64Image).find { it != null }
                    ?.let {
                        val bitmap = base64ToBitmap(it)
                        Box(
                            modifier = Modifier
                                .size(300.dp)
                                .clip(shape = RoundedCornerShape(8.dp))
                        ) {
                            Image(
                                painter = BitmapPainter(bitmap.asImageBitmap()),
                                contentDescription = "Selected Image",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop,
                            )
                            Box(
                                modifier = Modifier
                                    .align(
                                        Alignment.TopEnd
                                    )
                                    .padding(4.dp)
                                    .padding(4.dp)
                                    .clickable {
                                        if (initialQuestionId != null) {
                                            initialQuestion =
                                                initialQuestion?.copy(base64Image = null)
                                        } else {
                                            newQuestion = newQuestion.copy(base64Image = null)
                                        }
                                    }
                                    .clip(shape = CircleShape)
                                    .background(color = Color.White),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.Close,
                                    "Delete the image",
                                )
                            }
                        }

                    }
            }
        }
        itemsIndexed(options) { index, option ->
            TextField(
                leadingIcon = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(onClick = {
                            options[index] = option.copy(isCorrect = !option.isCorrect)
                        }, selected = option.isCorrect)
                        Icon(
                            Icons.Default.Delete,
                            "Delete an option",
                            modifier = Modifier.clickable { options.removeAt(index) })
                    }
                },
                value = option.text,
                onValueChange = { options[index] = option.copy(text = it) })
        }
        item {
            Button(onClick = {
                options.add(
                    QuizQuestionOption(
                        id = null,
                        text = "",
                        isCorrect = false,
                        quizQuestionId = -1
                    )
                )
            }) {
                Text("Add option")
            }
        }
        item {
            Button(onClick = {
                coroutineScope.launch {
                    if (initialQuestionId == null) {
                        when (val result = quizQuestionViewModel.createQuizQuestion(newQuestion)) {
                            is ApiResponse.Error -> Toast.makeText(
                                context,
                                result.message,
                                Toast.LENGTH_SHORT
                            ).show()

                            is ApiResponse.Success -> {
                                val createdQuestionId = result.data
                                val optionsWithCorrectId =
                                    options.map { option ->
                                        option.copy(quizQuestionId = createdQuestionId)
                                    }
                                when (val creationResponse =
                                    optionsViewModel.createMultiple(optionsWithCorrectId)) {
                                    is ApiResponse.Error -> Toast.makeText(
                                        context, creationResponse.message,
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    is ApiResponse.Success -> {
                                        navController.navigateUp()
                                    }
                                }
                            }
                        }
                    } else {
                        initialQuestion?.let { initialQuestion ->
                            when (val result = quizQuestionViewModel.updateQuizQuestion(
                                initialQuestion.id ?: -1,
                                initialQuestion
                            )) {
                                is ApiResponse.Error -> Toast.makeText(
                                    context, result.message,
                                    Toast.LENGTH_SHORT
                                ).show()

                                is ApiResponse.Success -> {
                                    val optionsWithCorrectQuestionId = options.map { option ->
                                        option.copy(
                                            quizQuestionId = initialQuestion.id
                                                ?: -1
                                        )
                                    }
                                    when (val replacementResult =
                                        optionsViewModel.replaceQuestionOptions(
                                            initialQuestion.id ?: -1,
                                            optionsWithCorrectQuestionId
                                        )) {
                                        is ApiResponse.Error -> Toast.makeText(
                                            context, replacementResult.message,
                                            Toast.LENGTH_SHORT
                                        ).show()

                                        is ApiResponse.Success -> {
                                            navController.navigateUp()
                                        }
                                    }
//                                    val deletionResponses =
//                                        optionsWithCorrectQuestionId.map { option ->
//                                            optionsViewModel.deleteQuizQuestionOption(
//                                                option.id ?: -1
//                                            )
//                                        }
//                                    val deletionResponsesError =
//                                        deletionResponses.find { it is ApiResponse.Error }
//                                    if (deletionResponsesError != null) {
//                                        Toast.makeText(
//                                            context,
//                                            (deletionResponsesError as ApiResponse.Error).message,
//                                            Toast.LENGTH_SHORT
//                                        ).show()
//                                    } else {
//                                        val creationResponses =
//                                            optionsWithCorrectQuestionId.map { option ->
//                                                optionsViewModel.createQuizQuestionOption(option)
//                                            }
//                                        val creationResponsesError =
//                                            creationResponses.find { it is ApiResponse.Error }
//                                        if (creationResponsesError != null) {
//                                            Toast.makeText(
//                                                context,
//                                                (creationResponsesError as ApiResponse.Error).message,
//                                                Toast.LENGTH_SHORT
//                                            ).show()
//                                        } else {
//                                            navController.navigateUp()
//                                        }
//                                    }
                                }
                            }
                        }
                    }
                }
            }) {
                Text("Confirm")
            }
        }
    }
}