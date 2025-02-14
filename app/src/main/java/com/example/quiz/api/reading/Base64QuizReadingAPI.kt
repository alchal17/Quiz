package com.example.quiz.api.reading

import com.example.quiz.api.ApiResponse
import com.example.quiz.models.request_representation.Base64Quiz
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json

class Base64QuizReadingAPI(override val client: HttpClient) : ReadingAPI<Base64Quiz>() {
    override val currentRoute: String
        get() = "/quiz"

    override val serializer: KSerializer<Base64Quiz>
        get() = Base64Quiz.serializer()

    override suspend fun getById(id: Int): ApiResponse<Base64Quiz> {
        val url = "$serverPath$currentRoute/find_base64_quiz_by_id?id=$id"
        return try {
            val result = client.get(url)
            if (result.status == HttpStatusCode.OK) {
                val data = Json.decodeFromString(serializer, result.bodyAsText())
                ApiResponse.Success(data = data)
            } else {
                ApiResponse.Error(message = result.bodyAsText())
            }
        } catch (e: Exception) {
            ApiResponse.Error(message = e.message ?: "Unknown error")
        }

    }

}