package com.example.quiz.presentation.navigation

import kotlinx.serialization.Serializable

sealed interface AppRoute {
    @Serializable
    object Auth : AppRoute

    @Serializable
    object Quiz : AppRoute
}