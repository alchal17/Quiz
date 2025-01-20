package com.example.quiz.ui.pages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.quiz.ui.elements.QuizCard
import com.example.quiz.viewmodels.QuizReadingViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainPage(userID: Int) {
    val quizReadingViewModel = koinViewModel<QuizReadingViewModel>()
    val quizzes = quizReadingViewModel.quizzes.collectAsState().value

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
        quizReadingViewModel.getAll()
    }
}