package com.udacity.asteroidProject3.network

import com.squareup.moshi.Json

data class NetworkPicture(
    @Json(name = "media_type")
    val mediaType: String,
    val title: String,
    val url: String
)
