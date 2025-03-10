package com.example.quiz.models.database_representation

import com.example.quiz.models.Model
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Quiz(
    override val id: Int? = null,
    val name: String,
    @SerialName("user_id")
    val userId: Int,
    val description: String?,
    @SerialName("image_path")
    val imagePath: String?,
): Model