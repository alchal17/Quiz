package com.example.quiz.api.reading

import com.example.quiz.api.ApiResponse
import com.example.quiz.models.database_representation.QuizUser
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import kotlinx.serialization.KSerializer

class QuizUserReadingAPI(override val client: HttpClient) : ReadingAPI<QuizUser>() {
    override val currentRoute: String
        get() = "/quiz_user"

    override val serializer: KSerializer<QuizUser>
        get() = QuizUser.serializer()

    suspend fun getUserByEmail(email: String): ApiResponse<QuizUser> {
        val url = "$serverPath$currentRoute/get_by_email?email=$email"
        return try {
            val response: HttpResponse = client.get(url)
            if (response.status == HttpStatusCode.OK) {
                ApiResponse.Success(data = response.body<QuizUser>())
            } else {
                ApiResponse.Error(message = response.bodyAsText())
            }
        } catch (e: Exception) {
            ApiResponse.Error(message = e.message ?: "Unknown error")
        }
    }

    suspend fun getUserByUsername(username: String): ApiResponse<QuizUser> {
        val url = "$serverPath$currentRoute/get_by_username?username=$username"
        return try {
            val response: HttpResponse = client.get(url)
            if (response.status == HttpStatusCode.OK) {
                ApiResponse.Success(data = response.body<QuizUser>())
            } else {
                ApiResponse.Error(message = response.bodyAsText())
            }
        } catch (e: Exception) {
            ApiResponse.Error(message = e.message ?: "Unknown error")
        }
    }
}