package com.example.quiz.api.writing

import com.example.quiz.models.request_representation.Base64QuizQuestion
import io.ktor.client.HttpClient
import kotlinx.serialization.KSerializer

class Base64QuizQuestionOptionsWritingAPI(override val client: HttpClient) :
    WritingAPI<Base64QuizQuestion>() {
    override val currentRoute: String
        get() = "/quiz"

    override val serializer: KSerializer<Base64QuizQuestion>
        get() = Base64QuizQuestion.serializer()
}