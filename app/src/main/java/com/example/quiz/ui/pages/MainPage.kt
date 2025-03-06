package com.example.quiz.ui.pages

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.quiz.api.ApiResponse
import com.example.quiz.models.database_representation.Quiz
import com.example.quiz.ui.elements.QuizCard
import com.example.quiz.viewmodels.QuizViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainPage(userID: Int) {
    val context = LocalContext.current

    val quizReadingViewModel = koinViewModel<QuizViewModel>()
    var quizzes by remember { mutableStateOf<List<Quiz>>(emptyList()) }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp)
        ) {
            items(quizzes) {
                QuizCard(it)
            }
        }
    }

    LaunchedEffect(Unit) {
        when (val response = quizReadingViewModel.getAllQuizzes()) {
            is ApiResponse.Error -> {
                Toast.makeText(context, response.message, Toast.LENGTH_SHORT).show()
            }

            is ApiResponse.Success -> {
                quizzes = response.data
            }
        }
    }
}