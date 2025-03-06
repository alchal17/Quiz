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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.quiz.R
import com.example.quiz.api.ApiResponse
import com.example.quiz.models.request_representation.Base64Quiz
import com.example.quiz.ui.base64ToBitmap
import com.example.quiz.ui.bitmapToBase64
import com.example.quiz.ui.theme.MainColor
import com.example.quiz.viewmodels.QuizQuestionViewModel
import com.example.quiz.viewmodels.QuizViewModel
import org.koin.androidx.compose.koinViewModel
import java.io.InputStream


@Composable
fun ManageQuizPage(
    userId: Int,
    navController: NavController,
    initialQuizId: Int?,
) {
    val quizViewModel = koinViewModel<QuizViewModel>()
    val questionViewModel = koinViewModel<QuizQuestionViewModel>()


    var initialBase64Quiz: Base64Quiz? by remember { mutableStateOf(null) }
    var newBase64Quiz by remember {
        mutableStateOf(
            //Creating a new empty quiz
            Base64Quiz(
                id = null,
                name = "",
                userId = userId,
                base64Image = null,
                description = null
            )
        )
    }

    val context = LocalContext.current

    val imagePickerLauncher =
        rememberLauncherForActivityResult(GetContent()) { uri: Uri? ->
            uri?.let {
                val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
                val bitmap = BitmapFactory.decodeStream(inputStream)
                if (initialQuizId == null) {
                    newBase64Quiz = newBase64Quiz.copy(base64Image = bitmapToBase64(bitmap))
                } else {
                    initialBase64Quiz =
                        initialBase64Quiz?.copy(base64Image = bitmapToBase64(bitmap))
                }
            }
        }



    LaunchedEffect(Unit) {
        if (initialQuizId != null) {
            initialBase64Quiz = when (val result = quizViewModel.getBase64QuizById(initialQuizId)) {
                is ApiResponse.Error -> {
                    Toast.makeText(context, "Error: ${result.message}", Toast.LENGTH_SHORT).show()
                    null
                }

                is ApiResponse.Success -> result.data
            }
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = if (initialQuizId == null) "Create a new quiz!" else "Update ${initialBase64Quiz?.name ?: ""}",
                    fontFamily = FontFamily(
                        Font(R.font.oswald_regular)
                    ),
                    color = Color.White,
                    fontSize = 30.sp
                )

                TextField(
                    value = if (initialQuizId == null) newBase64Quiz.name else initialBase64Quiz?.name
                        ?: "",
                    onValueChange = { newNameValue ->
                        if (initialQuizId == null) {
                            newBase64Quiz = newBase64Quiz.copy(name = newNameValue)
                        } else {
                            initialBase64Quiz = initialBase64Quiz?.copy(name = newNameValue)
                        }
                    },
                    label = { Text("Name") }
                )
                TextField(
                    value = if (initialQuizId == null) newBase64Quiz.description
                        ?: "" else initialBase64Quiz?.description
                        ?: "",
                    onValueChange = { newDescriptionValue ->
                        if (initialQuizId == null) {
                            newBase64Quiz = newBase64Quiz.copy(description = newDescriptionValue)
                        } else {
                            initialBase64Quiz =
                                initialBase64Quiz?.copy(description = newDescriptionValue)
                        }
                    },
                    label = { Text("Description") }
                )
                if (initialQuizId == null && newBase64Quiz.base64Image == null ||
                    initialQuizId != null && initialBase64Quiz?.base64Image == null
                ) {
                    Button(
                        onClick = {
                            imagePickerLauncher.launch("image/*")
                        },
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

                listOf(
                    initialBase64Quiz?.base64Image,
                    newBase64Quiz.base64Image
                ).find { it != null }
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
                                        if (initialQuizId != null) {
                                            initialBase64Quiz =
                                                initialBase64Quiz?.copy(base64Image = null)
                                        } else {
                                            newBase64Quiz = newBase64Quiz.copy(base64Image = null)
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
    }
}
