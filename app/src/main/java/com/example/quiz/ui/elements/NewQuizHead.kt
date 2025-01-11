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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
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
fun NewQuizHead(userId: Int, quizViewModel: QuizViewModel) {
    val quizName = quizViewModel.quizName.collectAsState().value
    val quizDescription = quizViewModel.quizDescription.collectAsState().value
    val base64Image = quizViewModel.base64Image.collectAsState().value

    val context = LocalContext.current

    val imagePickerLauncher =
        rememberLauncherForActivityResult(GetContent()) { uri: Uri? ->
            uri?.let {
                val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
                val bitmap = BitmapFactory.decodeStream(inputStream)
                val base64String = bitmapToBase64(bitmap)
                quizViewModel.setImage(base64String)
            }
        }



    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            value = quizName,
            onValueChange = { quizViewModel.setName(it) },
            label = { Text("Quiz name") })
        Spacer(modifier = Modifier.height(10.dp))
        TextField(
            value = quizDescription,
            onValueChange = { quizViewModel.setDescription(it) },
            label = { Text("Quiz description") })

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