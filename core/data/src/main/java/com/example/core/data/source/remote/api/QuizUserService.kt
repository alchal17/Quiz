package com.example.core.data.source.remote.api

import com.example.auth.data.source.remote.dto.QuizUserDto
import com.example.core.data.di.ServerPath
import com.example.core.data.source.remote.ApiCallResult
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import javax.inject.Inject

class QuizUserService @Inject constructor(
    private val client: HttpClient,
    @ServerPath serverPath: String
) {
    private val userServiceUrl = "$serverPath/quiz_user"


    suspend fun getQuizUserById(id: Int): ApiCallResult<QuizUserDto> {
        return try {
            val user = client.get("${userServiceUrl}/$id").body<QuizUserDto>()
            ApiCallResult.Success(user)
        } catch (e: ClientRequestException) {
            ApiCallResult.HttpError(
                code = e.response.status.value,
                message = e.message
            )
        } catch (e: ServerResponseException) {
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
    }

    suspend fun getQuizUserByEmail(email: String): ApiCallResult<QuizUserDto> {
        return try {
            val user = client.get("${userServiceUrl}/email/$email").body<QuizUserDto>()
            ApiCallResult.Success(user)
        } catch (e: ClientRequestException) {
            ApiCallResult.HttpError(
                code = e.response.status.value,
                message = e.message
            )
        } catch (e: ServerResponseException) {
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

    }

    suspend fun getQuizUserByUsername(username: String): ApiCallResult<QuizUserDto> {
        return try {
            val user = client.get("${userServiceUrl}/username/$username").body<QuizUserDto>()
            ApiCallResult.Success(user)
        } catch (e: ClientRequestException) {
            ApiCallResult.HttpError(
                code = e.response.status.value,
                message = e.message
            )
        } catch (e: ServerResponseException) {
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
    }

    suspend fun createQuizUser(quizUserDto: QuizUserDto): ApiCallResult<Int> {
        return try {
            val response = client.post(userServiceUrl) {
                contentType(ContentType.Application.Json)
                setBody(quizUserDto)
            }
            val id = response.body<QuizUserDto>().id!!
            ApiCallResult.Success(id)
        } catch (e: ClientRequestException) {
            ApiCallResult.HttpError(
                code = e.response.status.value,
                message = e.message
            )
        } catch (e: ServerResponseException) {
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

    }
}