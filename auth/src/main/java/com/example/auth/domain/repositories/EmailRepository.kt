package com.example.auth.domain.repositories

import kotlinx.coroutines.CoroutineScope

interface EmailRepository {
    suspend fun signIn(
        coroutineScope: CoroutineScope,
        onSuccess: suspend (email: String, photoUrl: String?) -> Unit,
        onError: suspend (Exception) -> Unit
    )

}