package com.example.quiz.di

import org.koin.dsl.module

val appModule = module {
    includes(dataModule, domainModule, presentationModule)
}