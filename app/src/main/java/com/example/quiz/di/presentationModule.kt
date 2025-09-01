package com.example.quiz.di

import com.example.quiz.presentation.launcher.viewmodels.LauncherViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val presentationModule = module {
    viewModelOf(::LauncherViewModel)
}