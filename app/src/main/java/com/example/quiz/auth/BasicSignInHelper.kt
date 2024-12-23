package com.example.quiz.auth

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher

interface BasicSignInHelper {
    fun signIn(
        signInLauncher: ActivityResultLauncher<Intent>,
    )

    fun handleAccountPick(
        activity: Activity,
        resultCode: Int,
        data: Intent?,
        onSuccess: (email: String) -> Unit,
        onPhotoUrl: (url: String?) -> Unit
    )

    fun signOut()
}