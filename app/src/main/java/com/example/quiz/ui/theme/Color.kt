package com.example.quiz.ui.theme

import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)


val MainColor = Color(0xFF4059AD)
val SecondaryColor1 = Color(0xFF6B9AC4)
val SecondaryColor2 = Color(0xFF97D8C4)
val SecondaryColor3 = Color(0xFFEFF2F1)
val SecondaryColor4 = Color(0xFFF4B942)


@Composable
fun mainTextFieldColors() = TextFieldDefaults.colors(
    focusedContainerColor = SecondaryColor3,
    unfocusedContainerColor = SecondaryColor3,
    unfocusedIndicatorColor = Color.Transparent,
    focusedIndicatorColor = Color.Transparent,
    focusedTextColor = Color.Black,
    unfocusedTextColor = Color.Black,
    focusedLabelColor = SecondaryColor2,
    unfocusedLabelColor = SecondaryColor2
)