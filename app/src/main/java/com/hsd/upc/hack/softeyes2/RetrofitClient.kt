package com.hsd.upc.hack.softeyes2

import retrofit2.Retrofit
import com.hsd.upc.hack.softeyes2.RetrofitClient
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private var retrofit: Retrofit? = null
    private const val BASE_URL = "https://api-us.restb.ai/vision/v2/"
    val retrofitInstance: Retrofit?
        get() {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit
        }
}