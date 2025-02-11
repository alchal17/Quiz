package com.example.quiz.ui.pages

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.quiz.ui.elements.QuizCard
import com.example.quiz.ui.routing.QuizRoutes
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun UserQuizzesPage(userId: Int, navController: NavController) {
    val quizReadingViewModel = koinViewModel<QuizReadingViewModel>()
    val quizzes by quizReadingViewModel.quizzes.collectAsState()

    val quizManagingViewModel: QuizManagingViewModel = koinViewModel { parametersOf(null) }

    val coroutineScope = rememberCoroutineScope()

    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item { Text("Your quizzes") }

        itemsIndexed(quizzes) { index, quiz ->
            QuizCard(
                quiz = quiz,
                bottomText = null,
                topOptions = listOf(
                    {
                        Icon(
                            Icons.Default.Edit,
                            "Edit ${quiz.name}",
                            modifier = Modifier.clickable {
                                navController.navigate(
                                    QuizRoutes.ManageQuizPage(
                                        headerText = "Edit ${quiz.name}",
                                        base64QuizId = quiz.id
                                    )
                                )
                            })
                    },
                    {
                        Icon(
                            Icons.Default.Delete,
                            "Delete ${quiz.name}",
                            modifier = Modifier.clickable {
                                coroutineScope.launch {
                                    quizManagingViewModel.deleteQuizById(quiz.id ?: 0)
                                    quizReadingViewModel.removeQuizByIndex(index)
                                }
                            })
                    })
            )
        }
    }

    LaunchedEffect(Unit) { quizReadingViewModel.getQuestionsByUserId(userId) }
}