package com.example.feb28.provider

import com.example.feb28.dataClass.ApiDemo
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class RetrofitProvider private constructor() {

    val apiService: ApiService

    companion object {
        private var instance: RetrofitProvider? = null
        fun getIns(): RetrofitProvider {
            if (instance == null) {
                instance = RetrofitProvider()
            }
            return instance!!
        }
    }

    init {
        val logger = HttpLoggingInterceptor()
        val builder = OkHttpClient.Builder()

        logger.setLevel(HttpLoggingInterceptor.Level.HEADERS)
        builder.addInterceptor(logger)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://reqres.in").addConverterFactory(GsonConverterFactory.create())
            .client(builder.build())
            .build()

        apiService = retrofit.create(ApiService::class.java)
    }

}

interface ApiService {
    @GET("/api/users")
    suspend fun getGalleryData(): Response<ApiDemo>
}