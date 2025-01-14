package com.example.quiz.ui.elements

import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts.GetContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import androidx.compose.ui.unit.dp
import com.chargemap.compose.numberpicker.NumberPicker
import com.example.quiz.R
import com.example.quiz.ui.base64ToBitmap
import com.example.quiz.ui.bitmapToBase64
import com.example.quiz.ui.theme.LapisLazuli
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

    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {

        Spacer(modifier = Modifier.height(20.dp))

        TextField(value = question.text, onValueChange = {
            val updatedQuestion = question.copy(text = it)
            quizViewModel.editBase64Question(quizQuestionIndex, updatedQuestion)
        })
        Button(
            onClick = { imagePickerLauncher.launch("image/*") },
            colors = ButtonDefaults.buttonColors(
                containerColor = LapisLazuli,
                contentColor = Color.White
            )
        ) {
            Row {
                Icon(
                    painter = painterResource(R.drawable.image_logo),
                    "Pick an image"
                )
                Text("Add an image")
            }
        }

        Row(
            modifier = Modifier.align(Alignment.Start),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Allow multiple correct options")
            RadioButton(selected = question.multipleChoices, onClick = {
                val currentMultipleChoices = question.multipleChoices
                val updatedQuestion = question.copy(multipleChoices = !currentMultipleChoices)
                quizViewModel.editBase64Question(quizQuestionIndex, updatedQuestion)
            })
        }

        base64Image?.let {
            val bitmap = base64ToBitmap(it)
            Image(
                contentScale = ContentScale.FillHeight,
                painter = BitmapPainter(bitmap.asImageBitmap()),
                contentDescription = "Selected Image",
                modifier = Modifier
                    .size(120.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Seconds to answer:")
            NumberPicker(
                value = question.secondsToAnswer,
                onValueChange = {
                    val updatedAnswer = question.copy(secondsToAnswer = it)
                    quizViewModel.editBase64Question(quizQuestionIndex, updatedAnswer)
                },
                range = 5..30
            )
        }

        Text("Add options(2-6)")

        LazyColumn(modifier = Modifier.fillMaxWidth(0.6f)) {
            itemsIndexed(question.options) { optionIndex, option ->
                QuestionOptionTextField(
                    option = option,
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


}