package com.cakii.nimble_android_challenge.data.entity

import com.google.gson.annotations.SerializedName

data class Survey(
    @SerializedName("title") val title: String = "",
    @SerializedName("description") val description: String = "",
    @SerializedName("cover_image_url") val coverImageUrl: String = ""
)