package com.example.quiz.data.mappers

import com.example.quiz.data.source.remote.dto.QuizUserDto
import com.example.common.domain.models.QuizUser

fun QuizUserDto.toQuizUser() = QuizUser(
    id = id,
    username = username,
    email = email
)