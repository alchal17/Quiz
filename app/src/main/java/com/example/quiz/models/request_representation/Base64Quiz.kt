package com.example.quiz.models.request_representation

import com.example.quiz.models.Model
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Base64Quiz(
    override val id: Int? = null,
    val name: String,
    @SerialName("user_id")
    val userId: Int,
    @SerialName("base64_image")
    val base64Image: String?,
    val description: String?,
) : Model
