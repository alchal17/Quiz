package com.example.quiz

import android.app.Application
import com.example.quiz.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class QuizApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@QuizApplication)
            modules(appModule)
        }
    }
}