package com.example.core.data.mappers

import com.example.auth.data.source.remote.dto.QuizUserDto
import com.example.core.domain.models.QuizUser


fun QuizUserDto.toQuizUser() = QuizUser(
    id = id,
    username = username,
    email = email
)

fun QuizUser.toQuizUserDto() = QuizUserDto(
    id = id,
    username = username,
    email = email
)