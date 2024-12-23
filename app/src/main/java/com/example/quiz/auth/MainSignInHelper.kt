package com.example.quiz.auth

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException


class MainSignInHelper(private val googleSignInClient: GoogleSignInClient) : BasicSignInHelper {
    override fun signIn(signInLauncher: ActivityResultLauncher<Intent>) {

        val signInIntent = googleSignInClient.signInIntent
        signInLauncher.launch(signInIntent)
    }

    override fun handleAccountPick(
        activity: Activity,
        resultCode: Int,
        data: Intent?,
        onSuccess: (email: String) -> Unit,
        onPhotoUrl: (url: String?) -> Unit
    ) {
        signOut()
        if (resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                account?.let {
                    handleSignIn(it, onSuccess, onPhotoUrl)
                }
            } catch (e: ApiException) {
                e.printStackTrace()
            }
        }
    }

    override fun signOut() {
        googleSignInClient.signOut()
    }

    private fun handleSignIn(
        account: GoogleSignInAccount,
        onSuccess: (email: String) -> Unit,
        onPhotoUrl: (url: String?) -> Unit
    ) {
        val email = account.email
        val url = account.photoUrl.toString()
        email?.let { onSuccess(it) }
        onPhotoUrl(url)
    }
}