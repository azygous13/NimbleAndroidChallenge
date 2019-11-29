package com.cakii.nimble_android_challenge.data.entity

import com.google.gson.annotations.SerializedName

data class Auth(
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("token_type") val tokenType: String,
    @SerializedName("expires_in") val expiresIn: String,
    @SerializedName("created_at") val createdAt: Int
)