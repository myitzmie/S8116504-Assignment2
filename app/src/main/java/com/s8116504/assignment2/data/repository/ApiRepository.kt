package com.s8116504.assignment2.data.repository

import com.s8116504.assignment2.data.api.ApiService
import com.s8116504.assignment2.data.model.*
import javax.inject.Inject

class ApiRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun login(username: String, password: String): LoginResponse {
        return apiService.login(LoginRequest(username, password))
    }

    suspend fun getDashboard(keypass: String): List<Entity> {
        val response = apiService.getDashboard(keypass)
        return response.entities.map {
            Entity(
                title = it.property1 ?: it.property2 ?: "Unknown",
                description = it.description ?: ""
            )
        }

    }
}