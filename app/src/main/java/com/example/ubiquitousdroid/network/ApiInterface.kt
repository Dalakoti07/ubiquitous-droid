package com.example.ubiquitousdroid.network

import okhttp3.MultipartBody
import retrofit2.http.*

interface ApiInterface {
    @GET("gallery/r/party/")
    suspend fun getAllImages(): getImagesApiResponse

    @Multipart
    @POST("image/")
    suspend fun postAnImage(@Header("Authorization") authHeader:String ,@Part file:MultipartBody.Part,@Part("album") album:String,
                            @Part("type") type:String,@Part("name") name:String,@Part("title") title:String,
                            @Part("description") description:String,@Part("disable_audio") disable_audio:Int) : getImagesApiResponse

    @GET("album/ogCGesN/images")
    suspend fun getImagesFromGallery(): getImagesApiResponse
}