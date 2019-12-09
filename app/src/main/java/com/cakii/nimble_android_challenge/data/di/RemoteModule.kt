package com.cakii.nimble_android_challenge.data.di

import android.content.Context
import com.cakii.nimble_android_challenge.data.interceptor.AuthInterceptor
import com.cakii.nimble_android_challenge.data.service.SurveyService
import com.cakii.nimble_android_challenge.utils.Prefs
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class RemoteModule(var baseUrl: String) {

    @Provides
    @Singleton
    fun provideService(retrofit: Retrofit): SurveyService =
        retrofit.create(SurveyService::class.java)

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        val gson = GsonBuilder()
            .create()
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(context: Context, prefs: Prefs): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val tokenInterceptor = TokenInterceptor().apply {
            setSessionToken(prefs.auth.accessToken)
        }
        val builder = OkHttpClient
            .Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(tokenInterceptor)
            .authenticator(AuthInterceptor(context, prefs))
        return builder.build()
    }

    @Singleton
    class TokenInterceptor: Interceptor {
        private var token = ""

        fun setSessionToken(token: String) {
            this.token = token
        }

        override fun intercept(chain: Interceptor.Chain): Response {
            var request = chain.request()

            if (request.header("No-Authentication") == null) {
                val httpUrl = request.url().newBuilder()
                    .addQueryParameter("access_token", token)
                    .build()
                request = chain.request().newBuilder().url(httpUrl).build()
            }

            return chain.proceed(request)
        }
    }
}