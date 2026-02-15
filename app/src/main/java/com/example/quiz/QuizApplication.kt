package com.example.quiz

import android.app.Application
import com.example.auth.di.authModule
import com.example.common.di.commonModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class QuizApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@QuizApplication)
            modules(commonModule, authModule)
        }
    }
}