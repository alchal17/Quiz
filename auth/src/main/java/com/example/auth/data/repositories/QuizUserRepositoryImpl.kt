package com.example.auth.data.repositories

import com.example.auth.data.mappers.toQuizUser
import com.example.auth.data.mappers.toQuizUserDto
import com.example.auth.data.source.remote.api.QuizUserService
import com.example.auth.domain.repositories.QuizUserRepository
import com.example.common.data.source.local.remote.ApiCallResult
import com.example.common.domain.DomainResult
import com.example.common.domain.models.QuizUser

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