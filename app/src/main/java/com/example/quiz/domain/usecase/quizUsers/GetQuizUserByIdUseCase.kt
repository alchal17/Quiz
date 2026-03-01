package com.example.quiz.domain.usecase.quizUsers

import com.example.common.domain.models.QuizUser
import com.example.common.domain.DomainResult
import com.example.quiz.domain.repositories.QuizUserRepository

class GetQuizUserByIdUseCase(private val quizUserRepository: QuizUserRepository) {
    suspend operator fun invoke(id: Int): DomainResult<QuizUser> = quizUserRepository.getById(id)
}