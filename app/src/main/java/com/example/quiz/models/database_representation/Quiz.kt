package com.example.quiz.models.database_representation

import com.example.quiz.models.Model
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Quiz(
    override val id: Int? = null,
    val name: String,
    val description: String,
    @SerialName("image_path")
    val imagePath: String?,
    val questions: List<QuizQuestion>
): Model