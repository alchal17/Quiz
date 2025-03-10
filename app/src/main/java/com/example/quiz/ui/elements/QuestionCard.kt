package com.example.quiz.ui.elements

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.quiz.models.database_representation.QuizQuestion

@Composable
fun QuestionCard(quizQuestion: QuizQuestion, onUpdateClick: () -> Unit, onDeleteClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .weight(4f)
        ) {
            Box(modifier = Modifier.weight(3f)) {
                Text(quizQuestion.text, modifier = Modifier.padding(10.dp))
            }
            Box(modifier = Modifier.weight(1f)) {
                quizQuestion.imagePath?.let { path ->
                    Image(
                        rememberAsyncImagePainter("images/get_image?path=$path"),
                        "${quizQuestion.text} image"
                    )
                }
            }
        }
        Row(modifier = Modifier.weight(1f)) {
            IconButton(onClick = onUpdateClick) {
                Icon(
                    Icons.Default.Edit,
                    "Edit ${quizQuestion.text} question"
                )
            }
            IconButton(onClick = onDeleteClick) {
                Icon(
                    Icons.Default.Delete,
                    "Delete ${quizQuestion.text} question"
                )
            }
        }
    }
}