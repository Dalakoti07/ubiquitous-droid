package com.example.ubiquitousdroid.models

import com.google.gson.annotations.SerializedName

data class imageUploadResponse(
    @SerializedName("success") val success:Boolean
    ,     @SerializedName("status") val statusCode:Int
)