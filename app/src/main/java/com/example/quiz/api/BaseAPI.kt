package com.example.quiz.api

import io.ktor.client.HttpClient

abstract class BaseAPI(protected val client: HttpClient) {
//    protected val serverPath = "http://192.168.0.104:8080"
    protected val serverPath = "http://192.168.88.26:8080"
}