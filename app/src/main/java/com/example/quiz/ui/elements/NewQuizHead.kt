package com.example.quiz.ui.elements

import android.graphics.BitmapFactory
import android.net.Uri
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quiz.R
import com.example.quiz.ui.base64ToBitmap
import com.example.quiz.ui.bitmapToBase64
import com.example.quiz.ui.theme.MainColor
import com.example.quiz.ui.theme.SecondaryColor4
import com.example.quiz.ui.theme.mainTextFieldColors
import com.example.quiz.viewmodels.QuizCreationViewModel
import org.koin.androidx.compose.koinViewModel
import java.io.InputStream

@Preview
@Composable
fun NewQuizHead() {
    val quizCreationViewModel = koinViewModel<QuizCreationViewModel>()

    val quizName = quizCreationViewModel.quizName.collectAsState().value
    val quizDescription = quizCreationViewModel.quizDescription.collectAsState().value
    val base64Image = quizCreationViewModel.base64Image.collectAsState().value

    val context = LocalContext.current

    val imagePickerLauncher =
        rememberLauncherForActivityResult(GetContent()) { uri: Uri? ->
            uri?.let {
                val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
                val bitmap = BitmapFactory.decodeStream(inputStream)
                val base64String = bitmapToBase64(bitmap)
                quizCreationViewModel.setImage(base64String)
            }
        }



    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Main information",
            style = TextStyle(SecondaryColor4),
            fontFamily = FontFamily(Font(R.font.oswald_regular)),
            fontSize = 25.sp,
        )
        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            modifier = Modifier.fillMaxWidth(0.8f),
            value = quizName,
            onValueChange = { quizCreationViewModel.setName(it) },
            label = { Text("Quiz name") },
            colors = mainTextFieldColors(),
            singleLine = true,
            shape = RoundedCornerShape(25)
        )
        Spacer(modifier = Modifier.height(10.dp))
        TextField(
            modifier = Modifier.fillMaxWidth(0.8f),
            value = quizDescription,
            onValueChange = { quizCreationViewModel.setDescription(it) },
            label = { Text("Quiz description") },
            colors = mainTextFieldColors(),
            maxLines = 3,
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
                    Text("Add an image", fontFamily = FontFamily(Font(R.font.oswald_light)))
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
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .align(
                            Alignment.TopEnd
                        )
                        .padding(4.dp)
                        .clickable { quizCreationViewModel.setImage(null) }
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