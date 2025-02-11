package com.example.quiz.ui.routing

import kotlinx.serialization.Serializable

sealed interface QuizRoutes {

    @Serializable
    data object MainPage : QuizRoutes

    @Serializable
    data object Settings : QuizRoutes

    @Serializable
    data class ManageQuizPage(
        val headerText: String = "Create a new quiz!",
        val base64QuizId: Int? = null
    ) :
        QuizRoutes

    @Serializable
    data object UserQuizzesPage : QuizRoutes
}