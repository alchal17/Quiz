package com.example.auth.data.mappers

import com.example.auth.data.source.remote.dto.QuizUserDto
import com.example.common.domain.models.QuizUser

fun QuizUserDto.toQuizUser() = QuizUser(
    id = id,
    username = username,
    email = email
)