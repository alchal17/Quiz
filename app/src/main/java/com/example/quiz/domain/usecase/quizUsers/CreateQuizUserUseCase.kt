package com.example.quiz.domain.usecase.quizUsers

import com.example.quiz.domain.DomainResult
import com.example.quiz.domain.models.QuizUser
import com.example.quiz.domain.repositories.QuizUserRepository

class CreateQuizUserUseCase(private val quizUserRepository: QuizUserRepository) {
    suspend operator fun invoke(quizUser: QuizUser): DomainResult<Int> =
        quizUserRepository.create(quizUser)
}