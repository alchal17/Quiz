package com.example.quiz.api.writing

import com.example.quiz.api.ApiResponse
import com.example.quiz.api.BaseAPI
import com.example.quiz.models.Model
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

abstract class WritingAPI<T: @Serializable Model> : BaseAPI<T>() {
    open suspend fun deleteById(id: Int): ApiResponse<String> {
        val url = "$serverPath$currentRoute/delete/$id"
        return try {
            val result = client.delete(url)
            if (result.status == HttpStatusCode.OK) {
                ApiResponse.Success(data = result.bodyAsText())
            } else {
                ApiResponse.Error(message = result.bodyAsText())
            }
        } catch (e: Exception) {
            ApiResponse.Error(message = e.message ?: "Unknown error")
        }
    }

    open suspend fun create(value: T): ApiResponse<Int> {
        val url = "$serverPath$currentRoute/create"
        return try {
            val result = client.post(url) {
                contentType(ContentType.Application.Json)
                setBody(Json.encodeToString(serializer, value))
            }
            if (result.status == HttpStatusCode.Created) {
                ApiResponse.Success(data = result.body<Int>())
            } else {
                ApiResponse.Error(result.bodyAsText())
            }
        } catch (e: Exception) {
            ApiResponse.Error(message = e.message ?: "Unknown error")
        }
    }

    open suspend fun update(value: T, id: Int): ApiResponse<String> {
        val url = "$serverPath$currentRoute/update/$id"
        return try {
            val result = client.put(url) {
                contentType(ContentType.Application.Json)
                setBody(Json.encodeToString(serializer, value))
            }
            if (result.status == HttpStatusCode.OK) {
                ApiResponse.Success(data = result.bodyAsText())
            } else {
                ApiResponse.Error(result.bodyAsText())
            }
        } catch (e: Exception) {
            ApiResponse.Error(message = e.message ?: "Unknown error")
        }

    }
}