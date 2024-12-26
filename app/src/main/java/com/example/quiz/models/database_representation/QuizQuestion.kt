package com.example.quiz.models.database_representation

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class QuizQuestion(
    val id: Int? = null,
    val quiz: Quiz,
    val text: String,
    val description: String?,
    @SerialName("image_path")
    val imagePath: String?,
    @SerialName("multiple_choices")
    val multipleChoices: Boolean,
    val options: List<QuizQuestionOption>
)
