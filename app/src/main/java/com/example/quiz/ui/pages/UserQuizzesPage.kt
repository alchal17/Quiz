package com.example.quiz.ui.pages

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.quiz.api.ApiResponse
import com.example.quiz.models.database_representation.Quiz
import com.example.quiz.ui.elements.DeletionDialogue
import com.example.quiz.ui.elements.QuizCard
import com.example.quiz.ui.routing.QuizRoutes
import com.example.quiz.viewmodels.QuizViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun UserQuizzesPage(userId: Int, navController: NavController) {
    val quizViewModel = koinViewModel<QuizViewModel>()
    var quizzes by remember { mutableStateOf(emptyList<Quiz>()) }

    var quizToBeDeleted by remember { mutableStateOf<Quiz?>(null) }

    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

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
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            LazyColumn(
                contentPadding = padding,
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxWidth(0.9f)
            ) {
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
                            {
                                Icon(
                                    Icons.Default.Delete,
                                    "Delete ${quiz.name}",
                                    modifier = Modifier.clickable { quizToBeDeleted = quiz })
                            }
                        )
                    )
                }
            }
        }
        quizToBeDeleted?.let { quiz ->
            DeletionDialogue(
                onDismissRequest = { quizToBeDeleted = null },
                name = quiz.name
            ) {
                coroutineScope.launch {
                    when (quizViewModel.deleteQuiz(quiz.id ?: -1)) {
                        is ApiResponse.Error -> {
                            Toast.makeText(context, "Unable to delete", Toast.LENGTH_SHORT).show()
                            quizToBeDeleted = null
                        }

                        is ApiResponse.Success -> {
                            when (val response = quizViewModel.getByUserId(userId)) {
                                is ApiResponse.Error -> {}
                                is ApiResponse.Success -> {
                                    quizToBeDeleted = null
                                    quizzes = response.data
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}