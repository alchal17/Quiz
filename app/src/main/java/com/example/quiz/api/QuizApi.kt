package com.example.quiz.api

import com.example.quiz.models.database_representation.Quiz
import com.example.quiz.models.request_representation.Base64Quiz
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.serialization.KSerializer

class QuizApi(client: HttpClient) : BaseAPI<Base64Quiz>(client) {
    override val currentRoute: String
        get() = "/quiz"

    override val serializer: KSerializer<Base64Quiz>
        get() = Base64Quiz.serializer()

    suspend fun getQuestionsByUserId(userId: Int): List<Quiz> {
        val url = "$serverPath$currentRoute/find_by_user_id?id=$userId"
        try {
            val results: List<Quiz> = client.get(url).body()
            return results
        } catch (e: Exception) {
            return emptyList()
        }
    }
}
