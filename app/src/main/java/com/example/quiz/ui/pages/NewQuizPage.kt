package com.example.quiz.ui.pages

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.quiz.viewmodels.QuizViewModel

@Composable
fun NewQuizPage(userId: Int, quizViewModel: QuizViewModel) {
    Text(userId.toString())
}