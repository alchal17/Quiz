package com.example.quiz.ui.elements

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun NewQuizHead() {
//    val quizCreationViewModel = koinViewModel<QuizManagingViewModel>()
//
//    val quizName = quizCreationViewModel.quizName.collectAsState().value
//    val quizDescription = quizCreationViewModel.quizDescription.collectAsState().value
//    val base64Image = quizCreationViewModel.base64Image.collectAsState().value
//
//    val context = LocalContext.current
//
//    val imagePickerLauncher =
//        rememberLauncherForActivityResult(GetContent()) { uri: Uri? ->
//            uri?.let {
//                val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
//                val bitmap = BitmapFactory.decodeStream(inputStream)
//                val base64String = bitmapToBase64(bitmap)
//                quizCreationViewModel.setImage(base64String)
//            }
//        }
//
//
//
//    Column(
//        modifier = Modifier.fillMaxSize(),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Text(
//            "Main information",
//            style = TextStyle(SecondaryColor4),
//            fontFamily = FontFamily(Font(R.font.oswald_regular)),
//            fontSize = 25.sp,
//        )
//        Spacer(modifier = Modifier.height(20.dp))
//        TextField(
//            modifier = Modifier.fillMaxWidth(0.8f),
//            value = quizName,
//            onValueChange = { quizCreationViewModel.setName(it) },
//            label = { Text("Quiz name") },
//            colors = mainTextFieldColors(),
//            singleLine = true,
//            shape = RoundedCornerShape(25)
//        )
//        Spacer(modifier = Modifier.height(10.dp))
//        TextField(
//            modifier = Modifier.fillMaxWidth(0.8f),
//            value = quizDescription,
//            onValueChange = { quizCreationViewModel.setDescription(it) },
//            label = { Text("Quiz description") },
//            colors = mainTextFieldColors(),
//            maxLines = 3,
//            shape = RoundedCornerShape(25)
//        )
//
//        Spacer(modifier = Modifier.height(20.dp))
//
//        if (base64Image == null) {
//            Button(
//                onClick = { imagePickerLauncher.launch("image/*") },
//                colors = ButtonDefaults.buttonColors(
//                    containerColor = MainColor,
//                    contentColor = Color.White
//                )
//            ) {
//                Row {
//                    Icon(
//                        painter = painterResource(R.drawable.image_logo),
//                        "Pick an image"
//                    )
//                    Text("Add an image", fontFamily = FontFamily(Font(R.font.oswald_light)))
//                }
//            }
//        }
//
//        base64Image?.let {
//            val bitmap = base64ToBitmap(it)
//
//            Box(
//                modifier = Modifier
//                    .size(300.dp)
//                    .clip(shape = RoundedCornerShape(8.dp))
//            ) {
//                Image(
//                    painter = BitmapPainter(bitmap.asImageBitmap()),
//                    contentDescription = "Selected Image",
//                    modifier = Modifier.fillMaxSize(),
//                    contentScale = ContentScale.Crop
//                )
//                Box(
//                    modifier = Modifier
//                        .align(
//                            Alignment.TopEnd
//                        )
//                        .padding(4.dp)
//                        .clickable { quizCreationViewModel.setImage(null) }
//                        .clip(shape = CircleShape)
//                        .background(color = Color.White),
//                    contentAlignment = Alignment.Center
//                ) {
//                    Icon(
//                        imageVector = Icons.Rounded.Close,
//                        "Delete the image",
//                    )
//                }
//            }
//        }
//    }
}