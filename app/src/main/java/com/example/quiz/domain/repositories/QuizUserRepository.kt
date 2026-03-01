package com.example.quiz.domain.repositories

import com.example.common.domain.DomainResult
import com.example.common.domain.models.QuizUser

interface QuizUserRepository {
    suspend fun getById(id: Int): DomainResult<QuizUser>

    suspend fun getByEmail(email: String): DomainResult<QuizUser>

    suspend fun getByUsername(username: String): DomainResult<QuizUser>

    suspend fun create(user: QuizUser): DomainResult<Int>

}