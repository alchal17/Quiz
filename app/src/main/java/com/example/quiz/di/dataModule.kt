package com.example.quiz.di

import com.example.quiz.WEB_CLIENT_ID
import com.example.quiz.data.repositories.EmailRepositoryImpl
import com.example.quiz.data.repositories.QuizUserRepositoryImpl
import com.example.quiz.data.source.remote.api.QuizUserService
import com.example.quiz.domain.repositories.EmailRepository
import com.example.quiz.domain.repositories.QuizUserRepository
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

val dataModule = module {
    factoryOf(::QuizUserService)
    factoryOf(::QuizUserRepositoryImpl) bind QuizUserRepository::class
    factory {
        EmailRepositoryImpl(
            context = get(),
            webClientId = WEB_CLIENT_ID
        )
    } bind EmailRepository::class
}