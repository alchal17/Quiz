package com.example.quiz.ui.routing

import kotlinx.serialization.Serializable

sealed interface QuizRoutes {

    @Serializable
    data object MainPage : QuizRoutes

    @Serializable
    data object Settings : QuizRoutes

    @Serializable
    data object NewQuizPage : QuizRoutes

    @Serializable
    data object UserQuizzesPage : QuizRoutes
}