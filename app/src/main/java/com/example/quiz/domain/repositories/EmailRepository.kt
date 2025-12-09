package com.example.quiz.domain.repositories

import kotlinx.coroutines.CoroutineScope

interface EmailRepository {
   suspend fun signIn(
        score: CoroutineScope,
        onSuccess: suspend (email: String) -> Unit,
        onError: suspend (Exception) -> Unit
    )

}