package com.example.ubiquitousdroid.network

import com.example.ubiquitousdroid.models.ImageObject
import com.google.gson.annotations.SerializedName

data class getImagesApiResponse(
    @SerializedName("data") val listOfImages:List<ImageObject>
)