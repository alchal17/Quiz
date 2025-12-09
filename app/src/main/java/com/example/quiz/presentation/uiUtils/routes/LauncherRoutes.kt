package com.example.quiz.presentation.uiUtils.routes

import kotlinx.serialization.Serializable

sealed interface LauncherRoutes {
    @Serializable
    data object Launcher : LauncherRoutes

    @Serializable
    data class Quiz(val userId: Int): LauncherRoutes

    @Serializable
    data object SignIn: LauncherRoutes

    @Serializable
    data object SignUp: LauncherRoutes
}