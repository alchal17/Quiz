package com.example.core.data.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ServerPath

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class WebClientID