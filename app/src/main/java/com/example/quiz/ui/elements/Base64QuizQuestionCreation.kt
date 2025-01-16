package com.example.quiz.ui.elements

import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts.GetContent
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.example.quiz.ui.base64ToBitmap
import com.example.quiz.ui.bitmapToBase64
import com.example.quiz.ui.theme.MainColor
import com.example.quiz.ui.theme.SecondaryColor4
import com.example.quiz.ui.theme.mainTextFieldColors
import com.example.quiz.viewmodels.QuizViewModel
import org.koin.androidx.compose.koinViewModel
import java.io.InputStream

@Composable
fun Base64QuizQuestionCreation(quizQuestionIndex: Int) {

    val quizViewModel = koinViewModel<QuizViewModel>()

    val question = quizViewModel.base64QuizQuestions.collectAsState().value[quizQuestionIndex]

    val context = LocalContext.current

    val base64Image = question.base64Image

    val imagePickerLauncher =
        rememberLauncherForActivityResult(GetContent()) { uri: Uri? ->
            uri?.let {
                val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
                val bitmap = BitmapFactory.decodeStream(inputStream)
                val base64String = bitmapToBase64(bitmap)
                val updatedQuestion = question.copy(base64Image = base64String)
                quizViewModel.editBase64Question(quizQuestionIndex, updatedQuestion)
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
                Text(
                    "Question ${quizQuestionIndex + 1}:",
                    style = TextStyle(SecondaryColor4),
                    fontFamily = FontFamily(Font(R.font.oswald_regular)),
                    fontSize = 25.sp,
                )
                Spacer(modifier = Modifier.height(20.dp))

                TextField(
                    value = question.text, onValueChange = {
                        val updatedQuestion = question.copy(text = it)
                        quizViewModel.editBase64Question(quizQuestionIndex, updatedQuestion)
                    },
                    label = { Text("Question text") },
                    colors = mainTextFieldColors(),
                    singleLine = true,
                    shape = RoundedCornerShape(25)
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
                    Box(modifier = Modifier.size(300.dp)) {
                        Image(
                            painter = BitmapPainter(bitmap.asImageBitmap()),
                            contentDescription = "Selected Image",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                        Icon(
                            imageVector = Icons.Rounded.Close,
                            "Delete the image",
                            modifier = Modifier
                                .align(
                                    Alignment.TopEnd
                                )
                                .padding(4.dp)
                                .clickable {
                                    val updatedQuestion = question.copy(base64Image = null)
                                    quizViewModel.editBase64Question(
                                        quizQuestionIndex,
                                        updatedQuestion
                                    )
                                }
                        )
                    }
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Allow multiple correct options",
                        fontFamily = FontFamily(Font(R.font.oswald_light))
                    )
                    RadioButton(selected = question.multipleChoices, onClick = {
                        val currentMultipleChoices = question.multipleChoices
                        val updatedQuestion =
                            question.copy(multipleChoices = !currentMultipleChoices)
                        quizViewModel.editBase64Question(quizQuestionIndex, updatedQuestion)
                    })
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        "Seconds to answer:",
                        fontFamily = FontFamily(Font(R.font.oswald_light))
                    )
                    NumberPicker(
                        value = question.secondsToAnswer,
                        onValueChange = {
                            val updatedAnswer = question.copy(secondsToAnswer = it)
                            quizViewModel.editBase64Question(quizQuestionIndex, updatedAnswer)
                        },
                        range = 5..30
                    )
                }
                Text("Add options(2-6)", fontFamily = FontFamily(Font(R.font.oswald_light)))
            }
        }


        itemsIndexed(question.options) { optionIndex, option ->
            QuestionOptionTextField(
                option = option,
                labelText = "Option ${optionIndex + 1}",
                onValueChange = {

                    val updatedOption = option.copy(text = it)
                    quizViewModel.editQuestionOption(
                        quizQuestionIndex,
                        optionIndex,
                        updatedOption
                    )

                },
                toggleCorrectQuestion = {

                    val updatedOption = option.copy(isCorrect = !option.isCorrect)
                    quizViewModel.editQuestionOption(
                        quizQuestionIndex,
                        optionIndex,
                        updatedOption
                    )

                },
                onDeleteClick = {
                    quizViewModel.deleteQuestionOption(
                        questionIndex = quizQuestionIndex,
                        optionIndex = optionIndex
                    )
                }
            )
        }
        item {
            IconButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = { quizViewModel.addEmptyQuestionOption(quizQuestionIndex) }) {
                Icon(Icons.Default.Add, contentDescription = "Add new option")
            }
        }
    }
}