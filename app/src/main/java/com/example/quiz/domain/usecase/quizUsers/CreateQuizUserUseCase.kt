package com.example.quiz.domain.usecase.quizUsers

import com.example.common.domain.DomainResult
import com.example.common.domain.models.QuizUser
import com.example.auth.domain.repositories.QuizUserRepository

class CreateQuizUserUseCase(private val quizUserRepository: QuizUserRepository) {
    suspend operator fun invoke(quizUser: QuizUser): DomainResult<Int> =
        quizUserRepository.create(quizUser)
}