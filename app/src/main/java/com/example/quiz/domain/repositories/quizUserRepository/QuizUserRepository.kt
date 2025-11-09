package com.example.quiz.domain.repositories.quizUserRepository

import com.example.quiz.domain.models.QuizUser
import com.example.quiz.domain.repositories.DomainResult

interface QuizUserRepository {
    suspend fun getById(id: Int): DomainResult<QuizUser>

    suspend fun getByEmail(email: String): DomainResult<QuizUser>

    suspend fun getByUsername(username: String): DomainResult<QuizUser>

    suspend fun create(user: QuizUser): DomainResult<Int>

}