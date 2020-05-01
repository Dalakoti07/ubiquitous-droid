package com.example.ubiquitousdroid.network

import com.google.gson.annotations.SerializedName

data class ImageObject(@SerializedName("id")
                       val id: String = "",
                       @SerializedName("title")
                       val name: String = "",
                       @SerializedName("url")
                       val url: String = "")