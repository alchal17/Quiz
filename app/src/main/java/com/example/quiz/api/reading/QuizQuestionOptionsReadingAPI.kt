package com.example.quiz.api.reading

import com.example.quiz.api.ApiResponse
import com.example.quiz.models.database_representation.QuizQuestionOption
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json

class QuizQuestionOptionsReadingAPI(override val client: HttpClient) :
    ReadingAPI<QuizQuestionOption>() {
    suspend fun findAllByQuizQuestionId(id: Int): ApiResponse<List<QuizQuestionOption>> {
        val url = "$serverPath$currentRoute/find_by_question_id?id=$id"
        return getEntityListByUrl1(url)
    }

     private suspend fun getEntityListByUrl1(url: String): ApiResponse<List<QuizQuestionOption>> {
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


    override val currentRoute: String
        get() = "/quiz_question_option"

    override val serializer: KSerializer<QuizQuestionOption>
        get() = QuizQuestionOption.serializer()
}