package com.example.quiz.domain.usecase.quizUsers

import com.example.quiz.domain.models.QuizUser
import com.example.quiz.domain.repositories.DomainResult
import com.example.quiz.domain.repositories.quizUserRepository.QuizUserRepository

class GetQuizUserByIdUseCase(private val quizUserRepository: QuizUserRepository) {
    suspend operator fun invoke(id: Int): DomainResult<QuizUser> = quizUserRepository.getById(id)
}