package com.example.core.data.di

import com.example.core.data.API_PATH
import com.example.core.data.WEB_CLIENT_ID
import com.example.core.data.repositories.QuizUserRepositoryImpl
import com.example.core.domain.repositories.QuizUserRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(Singleton::class)
internal abstract class CoreDataModule {
    companion object {
        @ServerPath
        @Provides
        @Singleton
        fun provideServerPath(): String = API_PATH

        @WebClientID
        @Provides
        @Singleton
        fun provideWebClientID(): String = WEB_CLIENT_ID

        @Provides
        @Singleton
        fun provideHttpClient(): HttpClient {
            return HttpClient {
                install(ContentNegotiation) {
                    json(Json {
                        ignoreUnknownKeys = true
                        isLenient = true
                        encodeDefaults = false
                    })
                }
            }
        }

    }

    @Binds
    abstract fun bindQuizUserRepository(impl: QuizUserRepositoryImpl): QuizUserRepository
}