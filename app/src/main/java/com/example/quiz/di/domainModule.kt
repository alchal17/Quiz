package com.example.quiz.di

import com.example.quiz.domain.usecase.quizUsers.CreateQuizUserUseCase
import com.example.quiz.domain.usecase.quizUsers.GetQuizUserByEmailUseCase
import com.example.quiz.domain.usecase.quizUsers.GetQuizUserByIdUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val domainModule = module {
    factoryOf(::GetQuizUserByIdUseCase)
    factoryOf(::GetQuizUserByEmailUseCase)
    factoryOf(::CreateQuizUserUseCase)
}