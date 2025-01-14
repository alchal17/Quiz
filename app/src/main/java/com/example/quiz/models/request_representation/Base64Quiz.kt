package com.example.quiz.models.request_representation

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Base64Quiz(
    val id: Int? = null,
    val name: String,
    @SerialName("user_id")
    val userId: Int,
    @SerialName("base64_image")
    val base64Image: String?,
    val description: String?,
    val questions: List<Base64QuizQuestion>
)
