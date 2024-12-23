package com.example.quiz.ui.routing

import kotlinx.serialization.Serializable

sealed interface QuizRoutes {
    @Serializable
    data class MainPage(val userId: Int)

    @Serializable
    data object Settings
}