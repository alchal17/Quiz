package com.example.quiz.data.mappers

import com.example.quiz.data.remote.dto.QuizUserDto
import com.example.quiz.domain.models.QuizUser

fun QuizUserDto.toQuizUser() = QuizUser(
    id = id,
    username = username,
    email = email
)