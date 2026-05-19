package br.com.corecode.wtcchallenge.data.network

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {
    @POST("/auth/login")
    suspend fun login(@Body payload: LoginPayload): Response<LoginResponse>
}