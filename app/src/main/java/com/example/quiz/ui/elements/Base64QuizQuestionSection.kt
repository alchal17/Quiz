package com.example.quiz.ui.elements

import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts.GetContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.quiz.R
import com.example.quiz.ui.base64ToBitmap
import com.example.quiz.ui.bitmapToBase64
import com.example.quiz.ui.theme.LapisLazuli
import com.example.quiz.viewmodels.QuizViewModel
import java.io.InputStream

@Composable
fun Base64QuizQuestionSection(index: Int, quizViewModel: QuizViewModel) {

    val question = quizViewModel.base64QuizQuestions.collectAsState().value[index]

    val context = LocalContext.current

    val base64Image = question.base64Image

    val imagePickerLauncher =
        rememberLauncherForActivityResult(GetContent()) { uri: Uri? ->
            uri?.let {
                val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
                val bitmap = BitmapFactory.decodeStream(inputStream)
                val base64String = bitmapToBase64(bitmap)
                val updatedQuestion = question.copy(base64Image = base64String)
                quizViewModel.editBase64Question(index, updatedQuestion)
            }
        }

    Column(modifier = Modifier.fillMaxSize()) {
        TextField(value = question.text, onValueChange = {
            val updatedQuestion = question.copy(text = it)
            quizViewModel.editBase64Question(index, updatedQuestion)
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
        base64Image?.let {
            val bitmap = base64ToBitmap(it)
            Image(
                painter = BitmapPainter(bitmap.asImageBitmap()),
                contentDescription = "Selected Image",
                modifier = Modifier.size(120.dp)
            )
        }
    }


}