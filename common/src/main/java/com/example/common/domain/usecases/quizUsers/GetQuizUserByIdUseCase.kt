package com.example.common.domain.usecases.quizUsers

import com.example.common.domain.models.QuizUser
import com.example.common.domain.DomainResult
import com.example.common.domain.repositories.QuizUserRepository

class GetQuizUserByIdUseCase(private val quizUserRepository: QuizUserRepository) {
    suspend operator fun invoke(id: Int): DomainResult<QuizUser> = quizUserRepository.getById(id)
}