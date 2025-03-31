package com.example.quiz.ui.elements

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun QuizDeletionDialogue(
    onDismissRequest: () -> Unit,
    quizName: String,
    onConfirmClick: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text("Confirmation") },
        text = { Text("Are you sure you want to delete $quizName?") },
        confirmButton = {
            TextButton(onClick = {
                onConfirmClick()
                onDismissRequest()
            }) {
                Text("Yes")
            }
        },
        dismissButton = { TextButton(onClick = onDismissRequest) { Text("No") } }
    )
}