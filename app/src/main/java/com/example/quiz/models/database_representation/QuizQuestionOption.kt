package com.example.quiz.models.database_representation

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class QuizQuestionOption(
    val id: Int? = null,
    val text: String,
    @SerialName("is_correct")
    val isCorrect: Boolean,
)
