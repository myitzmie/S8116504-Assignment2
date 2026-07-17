package com.s8116504.assignment2.data.api

import com.s8116504.assignment2.data.model.*
import retrofit2.http.*

interface ApiService {
    @POST("sydney/auth")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @GET("dashboard/{keypass}")
    suspend fun getDashboard(@Path("keypass") keypass: String): DashboardResponse
}