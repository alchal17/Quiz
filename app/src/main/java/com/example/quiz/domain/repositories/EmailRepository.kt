package com.example.quiz.domain.repositories

import kotlinx.coroutines.CoroutineScope

interface EmailRepository {
    suspend fun signIn(
        coroutineScope: CoroutineScope,
        onSuccess: suspend (email: String, photoUrl: String?) -> Unit,
        onError: suspend (Exception) -> Unit
    )

}