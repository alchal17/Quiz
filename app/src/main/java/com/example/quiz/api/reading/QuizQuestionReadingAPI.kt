package com.example.quiz.api.reading

import com.example.quiz.api.ApiResponse
import com.example.quiz.models.database_representation.QuizQuestion
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json

class QuizQuestionReadingAPI(override val client: HttpClient) : ReadingAPI<QuizQuestion>() {
    override val currentRoute: String
        get() = "/quiz_question"

    override val serializer: KSerializer<QuizQuestion>
        get() = QuizQuestion.serializer()

    suspend fun getQuestionsByQuizId(quizId: Int): ApiResponse<List<QuizQuestion>> {
        val url = "$serverPath$currentRoute/find_by_quiz_id?id=$quizId"
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


}