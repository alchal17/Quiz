package com.example.quiz.ui.routing

import kotlinx.serialization.Serializable

@Serializable
sealed interface QuizRoutes {

    @Serializable
    data object MainPage : QuizRoutes

    @Serializable
    data object Settings : QuizRoutes


    @Serializable
    data object UserQuizzesPage : QuizRoutes

    @Serializable
    sealed interface ManageQuiz : QuizRoutes {
        @Serializable
        data class QuizMainInfoPage(val base64QuizId: Int? = null) : ManageQuiz

        @Serializable
        data class QuestionInfoPage(val quizId: Int, val base64QuestionId: Int? = null) : ManageQuiz
    }
}