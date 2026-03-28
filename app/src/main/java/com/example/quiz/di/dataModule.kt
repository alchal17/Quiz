package com.example.quiz.di

import com.example.common.WEB_CLIENT_ID
import com.example.auth.data.repositories.EmailRepositoryImpl
import com.example.common.data.repositories.QuizUserRepositoryImpl
import com.example.auth.data.source.remote.api.QuizUserService
import com.example.common.domain.repositories.QuizUserRepository
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