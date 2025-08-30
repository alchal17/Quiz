package com.example.quiz.di

import com.example.quiz.data.sp.InnerStorage
import com.example.quiz.data.sp.InnerStorageImpl
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

val dataModule = module {
    factoryOf(::InnerStorageImpl) bind InnerStorage::class
}