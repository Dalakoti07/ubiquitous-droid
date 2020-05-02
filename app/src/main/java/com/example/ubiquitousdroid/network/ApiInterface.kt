package com.example.ubiquitousdroid.network

import com.example.ubiquitousdroid.models.imageUploadResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiInterface {
    @GET("gallery/r/party/")
    suspend fun getAllImages(@Header("Authorization") authHeader:String): getImagesApiResponse

    @Multipart
    @POST("upload")
    suspend fun postAnImage(@Header("Authorization") authHeader:String,
                            @Part file:MultipartBody.Part,
                            @Part("album") album:RequestBody,
                            @Part("type") type: RequestBody,
                            @Part("name") name:RequestBody,
                            @Part("title") title:RequestBody,
                            @Part("description") description:RequestBody,
                            @Part("disable_audio") disable_audio:RequestBody) : imageUploadResponse

    @GET("album/ogCGesN/images")
    suspend fun getImagesFromGallery(@Header("Authorization") authHeader:String): getImagesApiResponse
}