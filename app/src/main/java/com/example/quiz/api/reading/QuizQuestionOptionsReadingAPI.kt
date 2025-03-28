package com.example.quiz.api.reading

import com.example.quiz.api.ApiResponse
import com.example.quiz.models.database_representation.QuizQuestionOption
import io.ktor.client.HttpClient
import kotlinx.serialization.KSerializer

class QuizQuestionOptionsReadingAPI(override val client: HttpClient) :
    ReadingAPI<QuizQuestionOption>() {
    suspend fun findAllByQuizQuestionId(id: Int): ApiResponse<List<QuizQuestionOption>> {
        val url = "$serverPath$currentRoute/find_by_question_id?id=$id"
        return getEntityListByUrl(url)
    }


    override val currentRoute: String
        get() = "/quiz_question_option"

    override val serializer: KSerializer<QuizQuestionOption>
        get() = QuizQuestionOption.serializer()
}