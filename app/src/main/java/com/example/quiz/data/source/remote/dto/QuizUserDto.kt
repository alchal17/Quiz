package com.example.quiz.data.source.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class QuizUserDto(val id: Int? = null, val username: String, val email: String)