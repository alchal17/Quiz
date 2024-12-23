package com.example.quiz.ui.routing

import kotlinx.serialization.Serializable

sealed interface AuthRoutes {
    sealed interface Auth : AuthRoutes {
        @Serializable
        data object SignIn : Auth

        @Serializable
        data object SignUp : Auth
    }

//    @Serializable
//    data class MainPage(val id: Int)
}