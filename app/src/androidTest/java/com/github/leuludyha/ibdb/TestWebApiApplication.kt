package com.github.leuludyha.ibdb

import com.github.leuludyha.ibdb.webapi.WebApiApplication

class TestWebApiApplication : WebApiApplication() {
    override fun getBaseUrl(): String {
        return "http://127.0.0.1:8080"
    }
}