package com.example.launcher.presentation.uiStates

sealed class LaunchState {
    object Loading : LaunchState()
    object NotSignedIn : LaunchState()
    object AccountNotExists : LaunchState()
    data class SignedIn(val userId: Int) : LaunchState()
}