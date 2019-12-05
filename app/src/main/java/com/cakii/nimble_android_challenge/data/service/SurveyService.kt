package com.cakii.nimble_android_challenge.data.service

import com.cakii.nimble_android_challenge.data.entity.Auth
import com.cakii.nimble_android_challenge.data.entity.Survey
import io.reactivex.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface SurveyService {

    @GET("surveys.json?page=1&per_page=10")
    fun getSurveys(): Single<List<Survey>>

    @FormUrlEncoded
    @POST("oauth/token")
    fun auth(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("grant_type") grantType: String
    ): Single<Auth>
}