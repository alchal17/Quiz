package com.example.quiz.api.writing

import com.example.quiz.api.ApiResponse
import com.example.quiz.models.database_representation.QuizQuestionOption
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json

class QuizQuestionOptionsWritingAPI(override val client: HttpClient) :
    WritingAPI<QuizQuestionOption>() {
    override val currentRoute: String
        get() = "/quiz_question_option"

    override val serializer: KSerializer<QuizQuestionOption>
        get() = QuizQuestionOption.serializer()

    suspend fun replaceQuestionOptions(
        questionId: Int,
        options: List<QuizQuestionOption>
    ): ApiResponse<String> {
        val url = "$serverPath$currentRoute/change_options?id=$questionId"
        return try {
            val result = client.put(url) {
                contentType(ContentType.Application.Json)
                setBody(options)
            }
            if (result.status == HttpStatusCode.OK) {
                ApiResponse.Success(data = result.bodyAsText())
            } else {
                ApiResponse.Error(message = result.bodyAsText())
            }
        } catch (e: Exception) {
            ApiResponse.Error(message = e.message ?: "Unknown error")
        }
    }

    suspend fun createMultiple(options: List<QuizQuestionOption>): ApiResponse<List<Int>> {
        val url = "$serverPath$currentRoute/create_multiple"
        return try {
            val result = client.post(url) {
                contentType(ContentType.Application.Json)
                setBody(Json.encodeToString(listSerializer, options))
            }
            if (result.status == HttpStatusCode.Created) {
                val ids = result.body<List<Int>>()
                ApiResponse.Success(data = ids)
            } else {
                ApiResponse.Error(message = result.bodyAsText())
            }
        } catch (e: Exception) {
            ApiResponse.Error(message = e.message ?: "Unknown error")
        }
    }
}
