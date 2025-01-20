package com.example.quiz.models.database_representation

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Quiz(
    val id: Int? = null,
    val name: String,
    val description: String,
    @SerialName("image_path")
    val imagePath: String?,
    val questions: List<QuizQuestion>
)