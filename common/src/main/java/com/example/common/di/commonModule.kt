package com.example.common.di

import org.koin.dsl.module

val commonModule = module {
    includes(commonDataModule)
}