package br.com.corecode.wtcchallenge.data.network

import android.content.Context
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

data class LoginPayload(val email: String, val password: String, val fcmToken: String?)
data class LoginResponse(val token: String, val uid: String, val name: String, val role: String)

class AuthInterceptor(
    private val tokenProvider: () -> String?
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val requestBuilder = chain.request().newBuilder()

        tokenProvider()?.let { token ->
            requestBuilder.addHeader("Authorization", "Bearer $token")
        }

        return chain.proceed(requestBuilder.build())
    }
}

object RetrofitClient {

    private const val BASE_URL = "http://10.0.2.2:3003/"

    private fun createRetrofit(tokenProvider: () -> String?): Retrofit {

        val client = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(tokenProvider))
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getAuthService(): AuthApiService {
        return createRetrofit { null }
            .create(AuthApiService::class.java)
    }

    fun getChatService(tokenProvider: () -> String?): ChatApiService {
        return createRetrofit(tokenProvider)
            .create(ChatApiService::class.java)
    }
}