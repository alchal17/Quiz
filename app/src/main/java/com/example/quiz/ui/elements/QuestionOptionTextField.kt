package com.example.quiz.ui.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.quiz.models.database_representation.QuizQuestionOption
import com.example.quiz.ui.theme.mainTextFieldColors

@Composable
fun QuestionOptionTextField(
    option: QuizQuestionOption,
    labelText: String,
    onValueChange: (String) -> Unit,
    toggleCorrectQuestion: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Row(modifier = Modifier.height(100.dp), verticalAlignment = Alignment.CenterVertically) {
        TextField(
            label = { Text(labelText) },
            colors = mainTextFieldColors(),
            modifier = Modifier.weight(4f).fillMaxHeight(),
            value = option.text,
            onValueChange = onValueChange,
            singleLine = true,
            shape = RoundedCornerShape(topStartPercent = 25, bottomStartPercent = 25)
        )
        Column(
            modifier = Modifier
                .weight(1.5f)
                .fillMaxHeight()
                .clip(shape = RoundedCornerShape(topEndPercent = 25, topStartPercent = 25))
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                Text("Correct:")
                RadioButton(selected = option.isCorrect, onClick = toggleCorrectQuestion)
            }
            Box(modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .clickable { onDeleteClick() }
                .background(color = Color.Red), contentAlignment = Alignment.Center) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Delete option"
                )
            }
        }
    }

}