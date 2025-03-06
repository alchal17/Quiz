package com.example.quiz.api.reading

import com.example.quiz.models.request_representation.Base64QuizQuestion
import io.ktor.client.HttpClient
import kotlinx.serialization.KSerializer

class Base64QuizQuestionReadingAPI(override val client: HttpClient) :
    ReadingAPI<Base64QuizQuestion>() {
    override val currentRoute: String
        get() = "/quiz_question"

    override val serializer: KSerializer<Base64QuizQuestion>
        get() = Base64QuizQuestion.serializer()
}