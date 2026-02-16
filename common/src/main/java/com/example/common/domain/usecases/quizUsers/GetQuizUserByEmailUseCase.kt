package com.example.common.domain.usecases.quizUsers

import com.example.common.domain.DomainResult
import com.example.common.domain.models.QuizUser
import com.example.common.domain.repositories.QuizUserRepository

class GetQuizUserByEmailUseCase(private val quizUserRepository: QuizUserRepository) {
    suspend operator fun invoke(email: String): DomainResult<QuizUser> =
        quizUserRepository.getByEmail(email)
}