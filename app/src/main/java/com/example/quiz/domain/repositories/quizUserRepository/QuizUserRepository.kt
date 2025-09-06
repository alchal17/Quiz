package com.example.quiz.domain.repositories.quizUserRepository

import com.example.quiz.domain.models.QuizUser

interface QuizUserRepository {
    suspend fun getById(id: Int): Result<QuizUser?>

    suspend fun getByEmail(email: String): Result<QuizUser>

    suspend fun getByUsername(username: String): Result<QuizUser>

    suspend fun create(user: QuizUser): Result<Int>

}