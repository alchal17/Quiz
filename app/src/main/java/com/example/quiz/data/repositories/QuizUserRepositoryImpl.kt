package com.example.quiz.data.repositories

import com.example.quiz.data.mappers.toQuizUser
import com.example.quiz.data.mappers.toQuizUserDto
import com.example.quiz.data.source.remote.api.ApiCallResult
import com.example.quiz.data.source.remote.api.QuizUserService
import com.example.quiz.domain.models.QuizUser
import com.example.quiz.domain.DomainResult
import com.example.quiz.domain.repositories.QuizUserRepository

class QuizUserRepositoryImpl(private val quizUserService: QuizUserService) : QuizUserRepository {
    override suspend fun getById(id: Int): DomainResult<QuizUser> {
        return when (val result = quizUserService.getQuizUserById(id)) {
            is ApiCallResult.HttpError -> DomainResult.Error(
                result.message ?: "Error ${result.code}"
            )

            is ApiCallResult.Success -> DomainResult.Success(result.data.toQuizUser())
        }
    }

    override suspend fun getByEmail(email: String): DomainResult<QuizUser> {
        return when (val result = quizUserService.getQuizUserByEmail(email)) {
            is ApiCallResult.HttpError -> DomainResult.Error(
                result.message ?: "Error ${result.code}"
            )

            is ApiCallResult.Success -> DomainResult.Success(result.data.toQuizUser())
        }

    }

    override suspend fun getByUsername(username: String): DomainResult<QuizUser> {
        return when (val result = quizUserService.getQuizUserByUsername(username)) {
            is ApiCallResult.HttpError -> DomainResult.Error(
                result.message ?: "Error ${result.code}"
            )

            is ApiCallResult.Success -> DomainResult.Success(result.data.toQuizUser())
        }
    }

    override suspend fun create(user: QuizUser): DomainResult<Int> {
        return when (val result = quizUserService.createQuizUser(user.toQuizUserDto())) {
            is ApiCallResult.HttpError -> DomainResult.Error(
                result.message ?: "Error ${result.code}"
            )

            is ApiCallResult.Success -> DomainResult.Success(result.data)
        }

    }
}