package com.example.ubiquitousdroid.network

import com.google.gson.annotations.SerializedName

data class apiResponse(
    @SerializedName("data") val listOfImages:List<ImageObject>
)