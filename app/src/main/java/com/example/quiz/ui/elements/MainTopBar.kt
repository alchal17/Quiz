package com.example.quiz.ui.elements

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import com.example.quiz.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopBar(navigationIconContent: @Composable () -> Unit = {}) {
    TopAppBar(
        navigationIcon = navigationIconContent,
        title = {
            Text(
                "Askme",
                color = Color.White,
                fontFamily = FontFamily(Font(R.font.oswald_regular)),
                fontSize = 30.sp,
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
    )
}