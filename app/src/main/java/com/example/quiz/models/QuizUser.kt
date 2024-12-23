package com.example.quiz.models

import kotlinx.serialization.Serializable


@Serializable
data class QuizUser(val id: Int? = null, val username: String, val email: String)
