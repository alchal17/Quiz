package com.example.quiz.api

import com.example.quiz.models.database_representation.Quiz
import com.example.quiz.models.request_representation.Base64Quiz
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
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

    suspend fun getAll(): List<Quiz> {
        val url = "$serverPath$currentRoute/all"
        try{
            val results: List<Quiz> = client.get(url).body()
            return results
        } catch (e: Exception){
            return emptyList()
        }
    }

}
