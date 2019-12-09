package com.cakii.nimble_android_challenge.data.entity

import com.google.gson.annotations.SerializedName

data class AuthRequest(
    @SerializedName("username") val username: String,
    @SerializedName("password") val password: String,
    @SerializedName("grant_type") val grantType: String
)