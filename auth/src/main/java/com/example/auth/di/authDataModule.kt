package com.example.auth.di

import com.example.auth.data.repositories.EmailRepositoryImpl
import com.example.auth.data.repositories.QuizUserRepositoryImpl
import com.example.auth.data.source.remote.api.QuizUserService
import com.example.auth.domain.repositories.EmailRepository
import com.example.auth.domain.repositories.QuizUserRepository
import com.example.common.WEB_CLIENT_ID
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

internal val authDataModule = module {
    factory {
        EmailRepositoryImpl(
            context = get(),
            webClientId = WEB_CLIENT_ID
        )
    } bind EmailRepository::class

    factoryOf(::QuizUserService)

    factoryOf(::QuizUserRepositoryImpl) bind QuizUserRepository::class
}