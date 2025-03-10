package com.example.quiz.ui.elements

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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chargemap.compose.numberpicker.NumberPicker
import com.example.quiz.R
import com.example.quiz.api.ApiResponse
import com.example.quiz.ui.base64ToBitmap
import com.example.quiz.ui.bitmapToBase64
import com.example.quiz.ui.theme.MainColor
import com.example.quiz.ui.theme.SecondaryColor4
import com.example.quiz.ui.theme.mainTextFieldColors
import com.example.quiz.viewmodels.QuizQuestionViewModel
import org.koin.androidx.compose.koinViewModel
import java.io.InputStream

@Composable
fun Base64QuizQuestionCreation(quizQuestionIndex: Int) {

    val quizQuestionViewModel = koinViewModel<QuizQuestionViewModel>()

    val context = LocalContext.current

    var text by remember { mutableStateOf("") }
    var base64Image by remember { mutableStateOf<String?>(null) }
    var multipleChoices by remember { mutableStateOf(false) }
    var secondsToAnswer by remember { mutableIntStateOf(15) }

    val imagePickerLauncher =
        rememberLauncherForActivityResult(GetContent()) { uri: Uri? ->
            uri?.let {
                val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
                val bitmap = BitmapFactory.decodeStream(inputStream)
                base64Image = bitmapToBase64(bitmap)
            }
        }

    LaunchedEffect(Unit) {
        when (val response = quizQuestionViewModel.getBase64QuestionById(quizQuestionIndex)) {
            is ApiResponse.Error -> {
                Toast.makeText(context, response.message, Toast.LENGTH_SHORT).show()
            }

            is ApiResponse.Success -> {
                val base64QuizQuestion = response.data
                text = base64QuizQuestion.text
                base64Image = base64QuizQuestion.base64Image
                multipleChoices = base64QuizQuestion.multipleChoices
                secondsToAnswer = base64QuizQuestion.secondsToAnswer
            }
        }
    }


    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Question ${quizQuestionIndex + 1}",
                        style = TextStyle(SecondaryColor4),
                        fontFamily = FontFamily(Font(R.font.oswald_regular)),
                        fontSize = 25.sp,
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(
                        onClick = { },
                        modifier = Modifier.align(Alignment.CenterVertically)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(30.dp)
                                .clip(shape = RoundedCornerShape(10.dp))
                                .background(color = Color.Red),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Delete, "delete the question")
                        }
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                TextField(
                    value = text, onValueChange = {
                        text = it
                    },
                    label = { Text("Question text") },
                    colors = mainTextFieldColors(),
                    singleLine = true,
                    shape = RoundedCornerShape(25),
                    modifier = Modifier.fillMaxWidth(0.8f)
                )
                Spacer(modifier = Modifier.height(20.dp))
                if (base64Image == null) {
                    Button(
                        onClick = { imagePickerLauncher.launch("image/*") },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MainColor,
                            contentColor = Color.White
                        )
                    ) {
                        Row {
                            Icon(
                                painter = painterResource(R.drawable.image_logo),
                                "Pick an image"
                            )
                            Text(
                                "Add an image",
                                fontFamily = FontFamily(Font(R.font.oswald_light))
                            )
                        }
                    }
                }
                base64Image?.let {
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
                                .clickable { base64Image = null }
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
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Allow multiple correct options:",
                        fontFamily = FontFamily(Font(R.font.oswald_light))
                    )
                    RadioButton(selected = multipleChoices, onClick = {
                        multipleChoices = !multipleChoices
                    })
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        "Seconds to answer:",
                        fontFamily = FontFamily(Font(R.font.oswald_light))
                    )
                    NumberPicker(
                        dividersColor = Color.Black,
                        value = secondsToAnswer,
                        onValueChange = {
                            secondsToAnswer = it
                        },
                        range = 5..30
                    )
                }
                Text(
                    "Add options(2-6) and choose correct options:",
                    fontFamily = FontFamily(Font(R.font.oswald_light))
                )
            }
        }

        item {
            Spacer(Modifier.height(10.dp))
        }

//        itemsIndexed(question.options) { optionIndex, option ->
//
//            Box(modifier = Modifier.fillMaxWidth(0.8f)) {
//                QuestionOptionTextField(
//                    option = option,
//                    labelText = "Option ${optionIndex + 1}",
//                    onValueChange = {
//                        val updatedOption = option.copy(text = it)
//                        quizCreationViewModel.editQuestionOption(
//                            quizQuestionIndex,
//                            optionIndex,
//                            updatedOption
//                        )
//                    },
//                    toggleCorrectQuestion = {
//                        if (!question.multipleChoices) {
//                            quizCreationViewModel.clearCorrectOptions(quizQuestionIndex)
//                        }
//
//                        val updatedOption = option.copy(isCorrect = !option.isCorrect)
//                        quizCreationViewModel.editQuestionOption(
//                            quizQuestionIndex,
//                            optionIndex,
//                            updatedOption
//                        )
//
//                    },
//                    onDeleteClick = {
//                        quizCreationViewModel.deleteQuestionOption(
//                            questionIndex = quizQuestionIndex,
//                            optionIndex = optionIndex
//                        )
//                    },
//                    multipleOptions = question.multipleChoices,
//                    includeDeletion = question.options.size > 2
//                )
//            }
//            Spacer(modifier = Modifier.height(10.dp))
//        }
//        if (question.options.size < 6) {
//            item {
//                Button(
//                    modifier = Modifier.fillMaxWidth(0.8f),
//                    onClick = { quizCreationViewModel.addEmptyQuestionOption(quizQuestionIndex) }) {
//                    Row(verticalAlignment = Alignment.CenterVertically) {
//                        Icon(Icons.Default.Add, contentDescription = "Add new option")
//                        Text("Add an option")
//                    }
//                }
//            }
//        }
        item {
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}