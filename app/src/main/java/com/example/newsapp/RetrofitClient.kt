package com.example.newsapp

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

object RetrofitClient {
    private const val BASE_URL = "https://run.mocky.io/v3/"

    val interceptor = HttpLoggingInterceptor().apply {
        setLevel(HttpLoggingInterceptor.Level.BODY)
    }
    val client = OkHttpClient.Builder().addInterceptor(interceptor).build();


    val retrofitBuilder by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    fun newsService() = retrofitBuilder.create(NewsService::class.java)
}

interface NewsService {
    @GET("f4864c66-ee04-4e7f-88a2-2fbd912ca5ab")
    suspend fun getNews(): Response<News>
}