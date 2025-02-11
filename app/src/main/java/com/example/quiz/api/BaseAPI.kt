package com.example.quiz.api

import com.example.quiz.apiPath
import com.example.quiz.models.Model
import com.example.quiz.models.database_representation.Quiz
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

abstract class BaseAPI<T : @Serializable Model>(protected val client: HttpClient) {
    protected val serverPath = apiPath

    protected abstract val currentRoute: String

    protected abstract val serializer: KSerializer<T>


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

    open suspend fun getAll(): ApiResponse<List<T>> {
        val url = "$serverPath$currentRoute/all"
        return try {
            val result = client.get(url)
            if (result.status == HttpStatusCode.OK) {
                ApiResponse.Success(data = result.body<List<T>>())
            } else {
                ApiResponse.Error(message = result.bodyAsText())
            }
        } catch (e: Exception) {
            ApiResponse.Error(message = e.message ?: "Unknown error")
        }
    }

    open suspend fun getById(id: Int): ApiResponse<T> {
        val url = "$serverPath$currentRoute/$id"
        return try {
            val result = client.get(url)
            if (result.status == HttpStatusCode.OK) {
                val data = Json.decodeFromString(serializer, result.bodyAsText())
                ApiResponse.Success(data = data)
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
            if (result.status == HttpStatusCode.Created) {
                ApiResponse.Success(data = result.bodyAsText())
            } else {
                ApiResponse.Error(result.bodyAsText())
            }
        } catch (e: Exception) {
            ApiResponse.Error(message = e.message ?: "Unknown error")
        }

    }

}