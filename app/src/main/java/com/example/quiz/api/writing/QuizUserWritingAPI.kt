package com.example.quiz.api.writing

import com.example.quiz.models.database_representation.QuizUser
import io.ktor.client.HttpClient
import kotlinx.serialization.KSerializer

class QuizUserWritingAPI(override val client: HttpClient) : WritingAPI<QuizUser>() {
    override val currentRoute: String
        get() = "/quiz_user"

    override val serializer: KSerializer<QuizUser>
        get() = QuizUser.serializer()
}