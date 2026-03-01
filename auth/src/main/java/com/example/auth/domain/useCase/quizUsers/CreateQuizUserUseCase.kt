package com.example.auth.domain.useCase.quizUsers

import com.example.auth.domain.repositories.QuizUserRepository
import com.example.common.domain.DomainResult
import com.example.common.domain.models.QuizUser

class CreateQuizUserUseCase(private val quizUserRepository: QuizUserRepository) {
    suspend operator fun invoke(quizUser: QuizUser): DomainResult<Int> =
        quizUserRepository.create(quizUser)
}