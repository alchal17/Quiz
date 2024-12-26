package com.example.quiz.ui.routing

import kotlinx.serialization.Serializable

sealed interface QuizRoutes {
    @Serializable
    data object MainPage

    @Serializable
    data object Settings

    @Serializable
    data object NewQuizPage
}