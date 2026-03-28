package com.example.core.domain.repositories

import com.example.core.domain.DomainResult
import com.example.core.domain.models.QuizUser

interface QuizUserRepository {
    suspend fun getById(id: Int): DomainResult<QuizUser>

    suspend fun getByEmail(email: String): DomainResult<QuizUser>

    suspend fun getByUsername(username: String): DomainResult<QuizUser>

    suspend fun create(user: QuizUser): DomainResult<Int>

}