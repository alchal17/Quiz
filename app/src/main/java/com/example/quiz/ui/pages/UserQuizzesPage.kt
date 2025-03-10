package com.example.quiz.ui.pages

import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.quiz.api.ApiResponse
import com.example.quiz.models.database_representation.Quiz
import com.example.quiz.ui.elements.QuizCard
import com.example.quiz.ui.routing.QuizRoutes
import com.example.quiz.viewmodels.QuizViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun UserQuizzesPage(userId: Int, navController: NavController) {
    val quizViewModel = koinViewModel<QuizViewModel>()
    var quizzes by remember { mutableStateOf(emptyList<Quiz>()) }

    LaunchedEffect(Unit) {
        when (val response = quizViewModel.getByUserId(userId)) {
            is ApiResponse.Error -> {
                //TODO: handle an error when loading user's quizzes
            }

            is ApiResponse.Success -> {
                quizzes = response.data
            }
        }
    }

    Scaffold(containerColor = Color.Unspecified, floatingActionButton = {
        FloatingActionButton(onClick = {
            navController.navigate(QuizRoutes.ManageQuiz.QuizMainInfoPage())
        }) {
            Icon(
                Icons.Filled.Add,
                contentDescription = "Create a new quiz"
            )
        }
    }) { padding ->
        LazyColumn(contentPadding = padding) {
            items(quizzes) { quiz ->
                QuizCard(
                    quiz, topOptions = listOf(
                        {
                            Icon(
                                Icons.Default.Edit,
                                "Edit ${quiz.name}",
                                modifier = Modifier.clickable {
                                    navController.navigate(
                                        QuizRoutes.ManageQuiz.QuizMainInfoPage(
                                            base64QuizId = quiz.id
                                        )
                                    )
                                })
                        },
                    )
                )
            }
        }
    }
}