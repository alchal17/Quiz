package com.example.quiz.models.database_representation

import kotlinx.serialization.Serializable

@Serializable
data class QuizQuestionOption(
    val id: Int? = null,
    val text: String,
    val isCorrect: Boolean,
)
