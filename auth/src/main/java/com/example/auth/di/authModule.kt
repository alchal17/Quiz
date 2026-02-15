package com.example.auth.di

import org.koin.dsl.module

val authModule = module {
    includes(authDataModule, authDomainModule, authPresentationModule)
}