package com.example.quiz.models.request_representation

import com.example.quiz.models.database_representation.QuizUser
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Base64Quiz(
    val id: Int? = null,
    val name: String,
    val userId: Int,
    @SerialName("base64_image")
    val base64Image: String?,
    val questions: List<Base64QuizQuestion>
)
