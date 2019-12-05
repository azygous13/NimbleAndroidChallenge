package com.cakii.nimble_android_challenge.data.di

import com.cakii.nimble_android_challenge.data.service.SurveyService
import com.cakii.nimble_android_challenge.utils.Prefs
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class RemoteModule(var baseUrl: String) {

    @Provides
    @Singleton
    fun provideService(retrofit: Retrofit): SurveyService = retrofit.create(SurveyService::class.java)

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
    fun provideOkHttpClient(prefs: Prefs): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val urlInterceptor = Interceptor { chain ->
            val url = chain.request().url()
            val urlBuilder = url.newBuilder()
            if (url.encodedPath().contains("oauth/token")) {
                chain.proceed(chain.request())
            } else {
                prefs.auth.let {
                    urlBuilder.addQueryParameter("access_token", it.accessToken)
                }
                val httpUrl = urlBuilder.build()
                val request = chain.request().newBuilder().url(httpUrl).build()
                chain.proceed(request)
            }
        }
        val builder = OkHttpClient
                .Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(urlInterceptor)
        return builder.build()
    }
}