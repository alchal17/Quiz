package com.example.quiz.api.reading

import com.example.quiz.api.ApiResponse
import com.example.quiz.models.database_representation.Quiz
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json

class QuizReadingAPI(override val client: HttpClient) : ReadingAPI<Quiz>() {
    override val currentRoute: String
        get() = "/quiz"

    override val serializer: KSerializer<Quiz>
        get() = Quiz.serializer()

    suspend fun getByUserId(userId: Int): ApiResponse<List<Quiz>> {
        val url = "$serverPath$currentRoute/find_by_user_id?id=$userId"
        return try {
            val result = client.get(url)
            if (result.status == HttpStatusCode.OK) {
                val data = Json.decodeFromString(listSerializer, result.bodyAsText())
                ApiResponse.Success(data = data)
            } else {
                ApiResponse.Error(message = result.bodyAsText())
            }
        } catch (e: Exception) {
            ApiResponse.Error(message = e.message ?: "Unknown error")
        }

    }

    suspend fun getQuestionsNumber(quizId: Int): ApiResponse<Int> {
        val url = "$serverPath$currentRoute/get_questions_number?id=$quizId"
        return try {
            val result = client.get(url)
            if (result.status == HttpStatusCode.OK) {
                ApiResponse.Success(data = result.body())
            } else {
                ApiResponse.Error(message = result.bodyAsText())
            }
        } catch (e: Exception) {
            ApiResponse.Error(message = e.message ?: "Unknown error")
        }

    }
}