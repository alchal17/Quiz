package com.example.quiz.ui.elements

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.quiz.models.database_representation.QuizQuestionOption

@Composable
fun QuestionOptionTextField(
    option: QuizQuestionOption,
    onValueChange: (String) -> Unit,
    toggleCorrectQuestion: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Row(modifier = Modifier.height(100.dp)) {
        TextField(
            modifier = Modifier.weight(4f),
            value = option.text,
            onValueChange = onValueChange
        )
        Column(modifier = Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Correct answer")
                RadioButton(selected = option.isCorrect, onClick = toggleCorrectQuestion)
            }
            IconButton(onClick = onDeleteClick) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Delete option"
                )
            }
        }
    }

}