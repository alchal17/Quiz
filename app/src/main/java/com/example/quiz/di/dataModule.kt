package com.example.quiz.di

import com.example.quiz.data.repositories.QuizUserRepositoryImpl
import com.example.quiz.data.source.remote.api.QuizUserService
import com.example.quiz.data.source.local.InnerStorage
import com.example.quiz.data.source.local.InnerStorageImpl
import com.example.quiz.domain.repositories.quizUserRepository.QuizUserRepository
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

val dataModule = module {
    factoryOf(::InnerStorageImpl) bind InnerStorage::class

    single {
        HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                    encodeDefaults = false
                })
            }
        }
    }

    factoryOf(::QuizUserService)

    factoryOf(::QuizUserRepositoryImpl) bind QuizUserRepository::class
}