package com.example.quiz.data.remote.api

import com.example.quiz.domain.models.QuizUser
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.get

class QuizUserService(override val client: HttpClient) : BaseService() {
    companion object {
        private const val USER_SERVICE_URL = "$SERVER_PATH/quiz_user"
    }

    suspend fun getQuizUserById(id: Int): ApiCallResult<QuizUser> {
        return try {
            val user = client.get("$USER_SERVICE_URL/$id").body<QuizUser>()
            ApiCallResult.Success(user)
        } catch (e: ClientRequestException) {
            // 4xx errors (like 404 Not Found)
            ApiCallResult.HttpError(
                code = e.response.status.value,
                message = e.message
            )
        } catch (e: ServerResponseException) {
            // 5xx errors
            ApiCallResult.HttpError(
                code = e.response.status.value,
                message = e.message
            )
        } catch (e: Exception) {
            ApiCallResult.HttpError(
                code = -1,
                message = e.message ?: "Unknown error occurred"
            )
        }
    }}