package com.example.quiz.ui.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quiz.ui.elements.Base64QuizQuestionSection
import com.example.quiz.ui.elements.NewQuizHead
import com.example.quiz.ui.theme.Azure
import com.example.quiz.ui.theme.SandyBrown
import com.example.quiz.viewmodels.QuizViewModel
import kotlinx.coroutines.launch


@Composable
fun NewQuizPage(userId: Int, quizViewModel: QuizViewModel) {
    val base64Questions = quizViewModel.base64QuizQuestions.collectAsState().value

    val coroutineScope = rememberCoroutineScope()

    val pagerState = rememberPagerState(pageCount = { base64Questions.size + 1 })
    var previousQuestionCount by remember { mutableStateOf(base64Questions.size) }

    // Scroll to the new question when added
    LaunchedEffect(base64Questions.size) {
        if (base64Questions.size > previousQuestionCount) {
            coroutineScope.launch {
                pagerState.animateScrollToPage(base64Questions.size)
            }
        }
        previousQuestionCount = base64Questions.size
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            "Create a new quiz!",
            fontSize = 25.sp,
            style = TextStyle(color = SandyBrown),
            modifier = Modifier
                .padding(top = 5.dp)
                .align(Alignment.CenterHorizontally)
        )
        HorizontalPager(state = pagerState, modifier = Modifier.weight(1f)) { currentPage ->
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .background(shape = RoundedCornerShape(16.dp), color = Azure)
            ) {
                if (currentPage == 0) {
                    NewQuizHead(userId, quizViewModel)
                } else {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Base64QuizQuestionSection(index = currentPage - 1, quizViewModel)
                    }
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .align(Alignment.CenterHorizontally),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Previous",
                style = TextStyle(color = Color.Black.copy(alpha = if (pagerState.canScrollBackward) 1f else 0.5f)),
                modifier = Modifier.clickable {
                    if (pagerState.canScrollBackward) {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage - 1)
                        }
                    }
                })
            Text(if (pagerState.canScrollForward) "Next question" else "New question",
                style = TextStyle(color = Color.Black),
                modifier = Modifier.clickable {
                    coroutineScope.launch {
                        if (pagerState.canScrollForward) {
                            // Scroll to the next question
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        } else {
                            // If we can't scroll forward, add a new question
                            quizViewModel.addBase64Question()
                        }
                    }
                })
        }
    }
}
