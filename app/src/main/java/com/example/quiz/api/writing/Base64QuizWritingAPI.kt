package com.example.quiz.api.writing

import com.example.quiz.models.request_representation.Base64Quiz
import io.ktor.client.HttpClient
import kotlinx.serialization.KSerializer

class Base64QuizWritingAPI(override val client: HttpClient) : WritingAPI<Base64Quiz>() {
    override val currentRoute: String
        get() = "/quiz"

    override val serializer: KSerializer<Base64Quiz>
        get() = Base64Quiz.serializer()

}
