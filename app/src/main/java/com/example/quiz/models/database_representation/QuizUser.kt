package com.example.quiz.models.database_representation

import com.example.quiz.models.Model
import kotlinx.serialization.Serializable


@Serializable
data class QuizUser(override val id: Int? = null, val username: String, val email: String) : Model
