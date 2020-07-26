package com.example.atry.network

import com.squareup.moshi.Moshi
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

private const val BASE_URL = "https://api-conzoom.herokuapp.com/api/v1/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()


interface ConzoomApiService{
    @POST("usuarios/login")
    fun getLoginProperties(@Body login: Login):
            Deferred<LoginResponse>
}

object ConzoomApi {
    val retrofitService: ConzoomApiService by lazy {
        retrofit.create(ConzoomApiService::class.java)
    }
}

