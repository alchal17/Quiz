package com.example.quiz.data.mappers

import com.example.quiz.data.source.remote.dto.QuizUserDto
import com.example.quiz.domain.models.QuizUser

fun QuizUser.toQuizUserDto() = QuizUserDto(
    id = id,
    username = username,
    email = email
)