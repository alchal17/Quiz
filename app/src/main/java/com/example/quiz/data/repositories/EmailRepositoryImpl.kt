package com.example.quiz.data.repositories

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import com.example.quiz.domain.repositories.EmailRepository
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class EmailRepositoryImpl(
    private val context: Context,
    private val webClientId: String
) : EmailRepository {

    private val credentialManager = CredentialManager.create(context)

    override suspend fun signIn(
        scope: CoroutineScope,
        onSuccess: suspend (email: String) -> Unit,
        onError: suspend (Exception) -> Unit
    ) {
        val googleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(webClientId)
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        scope.launch {
            try {
                val result = credentialManager.getCredential(
                    request = request,
                    context = context
                )
                handleSignIn(result, onSuccess, onError)
            } catch (e: Exception) {
                onError(e)
            }
        }
    }

    private suspend fun handleSignIn(
        result: GetCredentialResponse,
        onSuccess: suspend (email: String) -> Unit,
        onError: suspend (Exception) -> Unit
    ) {
        when (val credential = result.credential) {
            is CustomCredential -> {
                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    try {
                        val googleIdTokenCredential = GoogleIdTokenCredential
                            .createFrom(credential.data)

                        val email = googleIdTokenCredential.id

                        onSuccess(email)
                    } catch (e: Exception) {
                        onError(e)
                    }
                }
            }

            else -> {
                onError(Exception("Unexpected credential type"))
            }
        }
    }
}