package com.example.ubiquitousdroid.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path

interface ApiInterface {
    @GET("gallery/r/game/")
    suspend fun getAllImages(): apiResponse
}