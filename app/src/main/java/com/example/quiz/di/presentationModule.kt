package com.example.quiz.di

import com.example.quiz.presentation.launcher.viewmodels.LauncherViewModel
import com.example.quiz.presentation.main.viewmodels.MainViewModel
import com.example.quiz.presentation.signIn.viewmodels.SignInViewModel
import com.example.quiz.presentation.signUp.viewmodels.SignUpViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val presentationModule = module {
    viewModelOf(::LauncherViewModel)
    viewModelOf(::SignInViewModel)
    viewModelOf(::SignUpViewModel)
    viewModel { args ->
        MainViewModel(
            innerStorage = get(),
            userId = args.get(),
            getQuizUserByIdUseCase = get()
        )
    }
}