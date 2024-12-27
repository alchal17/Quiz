package com.example.quiz.models.request_representation

import com.example.quiz.models.database_representation.Quiz
import com.example.quiz.models.database_representation.QuizQuestionOption
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Base64QuizQuestion(
    val id: Int? = null,
    val text: String,
    val description: String?,
    @SerialName("base64_image")
    val base64Image: String?,
    @SerialName("multiple_choices")
    val multipleChoices: Boolean,
    val options: List<QuizQuestionOption>
)
