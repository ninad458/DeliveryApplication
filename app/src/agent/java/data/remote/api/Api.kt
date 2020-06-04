package com.enigma.myapplication.data.remote.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface Api {

    companion object {
        fun getApi(): Api {
            val client = OkHttpClient
                .Builder()
                .addInterceptor(MockInterceptor())
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl("https://www.google.com/")
                .addConverterFactory(MoshiConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(Api::class.java)
        }
    }

    @GET("tasks")
    suspend fun getTasks(): GetTasksResponse

    @POST("tasks")
    suspend fun updateTaskStatus(@Body taskStatus: TaskStatusRequestBody): UpdateTasksResponse
}