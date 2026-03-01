package com.example.common.data.source.local.remote

import com.example.common.API_PATH
import io.ktor.client.HttpClient

abstract class BaseService {

    protected abstract val client: HttpClient

    companion object {
        protected const val SERVER_PATH = API_PATH
    }
}