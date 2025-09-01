package com.example.quiz.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.quiz.presentation.launcher.pages.LauncherPage
import com.example.quiz.ui.theme.QuizTheme

class LauncherActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            QuizTheme {
                LauncherPage()
            }
        }
    }
}