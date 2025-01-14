package com.example.quiz.ui.pages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.quiz.models.database_representation.QuizUser
import com.example.quiz.viewmodels.QuizUserViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainPage(userID: Int) {
    val quizUserViewModel = koinViewModel<QuizUserViewModel>()

    var user by remember { mutableStateOf<QuizUser?>(null) }

    if (user == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        user?.let { quizUser ->
            Column {
                Text(quizUser.email)
                Text(quizUser.username)
            }
        }
    }
    LaunchedEffect(Unit) { user = quizUserViewModel.findUserById(userID) }
}