package com.example.quiz.api

import com.example.quiz.models.request_representation.Base64Quiz
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class QuizApi(client: HttpClient) : BaseAPI(client) {
    private val currentRoute = "/quiz"

    suspend fun saveQuiz(base64Quiz: Base64Quiz) {
        val url = "$serverPath$currentRoute/create"
        client.post(url) {
            contentType(ContentType.Application.Json)
            setBody(base64Quiz)
        }
    }

}
