package com.example.quiz.api.reading

import com.example.quiz.models.database_representation.Quiz
import io.ktor.client.HttpClient
import kotlinx.serialization.KSerializer

class QuizReadingAPI(override val client: HttpClient): ReadingAPI<Quiz>() {
    override val currentRoute: String
        get() = "/quiz"

    override val serializer: KSerializer<Quiz>
        get() = Quiz.serializer()
}