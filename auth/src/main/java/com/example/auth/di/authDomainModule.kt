package com.example.auth.di

import com.example.auth.domain.useCase.quizUsers.CreateQuizUserUseCase
import com.example.auth.domain.useCase.quizUsers.GetQuizUserByEmailUseCase
import com.example.auth.domain.useCase.quizUsers.GetQuizUserByIdUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module


internal val authDomainModule = module {
    factoryOf(::CreateQuizUserUseCase)
    factoryOf(::GetQuizUserByEmailUseCase)
    factoryOf(::GetQuizUserByIdUseCase)
}