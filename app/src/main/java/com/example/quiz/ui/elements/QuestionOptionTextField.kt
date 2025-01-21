package com.example.quiz.ui.elements

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.quiz.models.database_representation.QuizQuestionOption
import com.example.quiz.ui.theme.mainTextFieldColors

@Composable
fun QuestionOptionTextField(
    option: QuizQuestionOption,
    labelText: String,
    onValueChange: (String) -> Unit,
    toggleCorrectQuestion: () -> Unit,
    onDeleteClick: () -> Unit,
    multipleOptions: Boolean,
    includeDeletion: Boolean
) {
    TextField(
        leadingIcon = {
            if (multipleOptions) {
                Checkbox(
                    checked = option.isCorrect,
                    onCheckedChange = { toggleCorrectQuestion() },
                    colors = CheckboxDefaults.colors(
                        checkmarkColor = Color.Black,
                        uncheckedColor = Color.Black,
                    )
                )
            } else {
                RadioButton(
                    selected = option.isCorrect,
                    onClick = toggleCorrectQuestion,
                    colors = RadioButtonDefaults.colors(selectedColor = Color.Black)
                )
            }
        },
        trailingIcon = {
            if (includeDeletion) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Delete the option",
                    modifier = Modifier.clickable {
                        onDeleteClick()
                    })
            }
        },
        label = { Text(labelText) },
        colors = mainTextFieldColors(),
        modifier = Modifier
            .fillMaxHeight(),
        value = option.text,
        onValueChange = onValueChange,
        singleLine = true,
        shape = RoundedCornerShape(25)
    )
}