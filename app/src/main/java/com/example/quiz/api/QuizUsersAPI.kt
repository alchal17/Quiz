package com.example.quiz.api

import com.example.quiz.models.database_representation.QuizUser
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType


class QuizUsersAPI(client: HttpClient) : BaseAPI<QuizUser>(client) {
    override val currentRoute = "/quiz_user"
    override val serializer = QuizUser.serializer()


    suspend fun getUserByEmail(email: String): Result<QuizUser> {
        val url = "$serverPath$currentRoute/get_by_email?email=$email"

        return try {
            val response: HttpResponse = client.get(url)

            when (response.status) {
                HttpStatusCode.OK -> {
                    val user: QuizUser = response.body<QuizUser>()
                    Result.success(user)
                }

                HttpStatusCode.NotFound -> {
                    Result.failure(Exception("User not found"))
                }

                HttpStatusCode.BadRequest -> {
                    Result.failure(Exception("Invalid email format"))
                }

                else -> {
                    Result.failure(Exception("Unexpected status: ${response.status}"))
                }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getUserByUsername(username: String): Result<QuizUser> {
        val url =
            "$serverPath$currentRoute/get_by_username?username=$username"

        return try {
            val response: HttpResponse = client.get(url)

            when (response.status) {
                HttpStatusCode.OK -> {
                    val user: QuizUser = response.body()
                    Result.success(user)
                }

                HttpStatusCode.NotFound -> {
                    Result.failure(Exception("User not found"))
                }

                HttpStatusCode.BadRequest -> {
                    Result.failure(Exception("Invalid username format"))
                }

                else -> {
                    Result.failure(Exception("Unexpected status: ${response.status}"))
                }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun createUser(user: QuizUser): Int {
        val url = "$serverPath$currentRoute/create"
        return client.post(url) {
            contentType(ContentType.Application.Json)
            setBody(user)
        }.body()
    }
}
