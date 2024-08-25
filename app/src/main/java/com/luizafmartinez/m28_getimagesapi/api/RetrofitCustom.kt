package com.luizafmartinez.m28_getimagesapi.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RetrofitCustom {
    // https://api.imgur.com/3/gallery/search/?q=cats
    val ImgurAPI by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.imgur.com/3/")
            .addConverterFactory( GsonConverterFactory.create() )
            .client(
                OkHttpClient.Builder()
                    .addInterceptor( AuthInterceptor() )
                    .build()
            )
            .build()
            .create( ImgurAPI::class.java) //Precisa passar a Interface, criada no pkg api

    }

}