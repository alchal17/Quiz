package com.example.quiz.di

import android.app.Application
import com.example.quiz.api.writing.Base64QuizWritingAPI
import com.example.quiz.auth.BasicSignInHelper
import com.example.quiz.auth.MainSignInHelper
import com.example.quiz.viewmodels.QuizQuestionOptionViewModel
import com.example.quiz.viewmodels.QuizQuestionViewModel
import com.example.quiz.viewmodels.QuizUserViewModel
import com.example.quiz.viewmodels.QuizViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appModule = module {
    single {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        GoogleSignIn.getClient(get<Application>(), gso)
    }

    singleOf(::MainSignInHelper) { bind<BasicSignInHelper>() }

    single {
        HttpClient(CIO) {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
        }
    }


    singleOf(::QuizUsersAPI)
    singleOf(::Base64QuizWritingAPI)
    singleOf(::QuizAPI)
    singleOf(::QuizQuestionsAPI)
    singleOf(::QuizQuestionOptionsAPI)

    viewModelOf(::QuizUserViewModel)
    viewModelOf(::QuizViewModel)
    viewModelOf(::QuizQuestionViewModel)
    viewModelOf(::QuizQuestionOptionViewModel)
}
