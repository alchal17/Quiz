package com.example.quiz

import android.app.Application
import com.example.quiz.di.appModule
import com.example.quiz.di.dataModule
import com.example.quiz.di.domainModule
import com.example.quiz.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class QuizApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@QuizApplication)
            modules(dataModule, domainModule, presentationModule, appModule)
        }
    }
}