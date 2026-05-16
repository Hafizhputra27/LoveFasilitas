package com.example.lovefasilitas.data.remote

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {

    // === Ganti BASE_URL sesuai environment ===

    // InfinityFree (production):
    private const val BASE_URL = "http://lovefasilitas.gt.tc/api/"

    // Emulator Android → localhost XAMPP:
    // private const val BASE_URL = "http://10.0.2.2/api/"

    // Device fisik (WiFi) → IP komputer:
    // private const val BASE_URL = "http://192.168.xxx.xxx/api/"

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: ApiService = retrofit.create(ApiService::class.java)
}
