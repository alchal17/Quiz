package com.example.quiz.data.mappers

import com.example.auth.data.source.remote.dto.QuizUserDto
import com.example.common.domain.models.QuizUser

fun QuizUser.toQuizUserDto() = QuizUserDto(
    id = id,
    username = username,
    email = email
)