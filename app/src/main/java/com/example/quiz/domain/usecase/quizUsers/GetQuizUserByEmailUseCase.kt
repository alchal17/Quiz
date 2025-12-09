package com.example.quiz.domain.usecase.quizUsers

import com.example.quiz.domain.models.QuizUser
import com.example.quiz.domain.DomainResult
import com.example.quiz.domain.repositories.QuizUserRepository

class GetQuizUserByEmailUseCase(private val quizUserRepository: QuizUserRepository) {
    suspend operator fun invoke(email: String): DomainResult<QuizUser> =
        quizUserRepository.getByEmail(email)
}