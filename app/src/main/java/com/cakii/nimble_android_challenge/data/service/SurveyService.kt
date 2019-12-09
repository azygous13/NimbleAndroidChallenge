package com.cakii.nimble_android_challenge.data.service

import com.cakii.nimble_android_challenge.data.entity.Auth
import com.cakii.nimble_android_challenge.data.entity.Survey
import io.reactivex.Single
import retrofit2.http.*

interface SurveyService {

    @GET("surveys.json?page=1&per_page=10")
    fun getSurveys(): Single<List<Survey>>

    @FormUrlEncoded
    @Headers("No-Authentication: true")
    @POST("oauth/token")
    fun auth(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("grant_type") grantType: String
    ): Single<Auth>
}