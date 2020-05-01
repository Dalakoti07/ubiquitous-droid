package com.example.ubiquitousdroid.models

import com.google.gson.annotations.SerializedName

data class ImageObject(@SerializedName("id")
                       val id: String = "",
                       @SerializedName("title")
                       val name: String = "",
                       @SerializedName("link")
                       val url: String = "")