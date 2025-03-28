package com.example.quiz.api.reading

import com.example.quiz.api.ApiResponse
import com.example.quiz.models.request_representation.Base64QuizQuestion
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json

class Base64QuizQuestionReadingAPI(override val client: HttpClient) :
    ReadingAPI<Base64QuizQuestion>() {
    override val currentRoute: String
        get() = "/quiz_question"

    override val serializer: KSerializer<Base64QuizQuestion>
        get() = Base64QuizQuestion.serializer()

    override suspend fun getById(id: Int): ApiResponse<Base64QuizQuestion> {
        val url = "$serverPath$currentRoute/find_base64_question_by_id?id=$id"

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