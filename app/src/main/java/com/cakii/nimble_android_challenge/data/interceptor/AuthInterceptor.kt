package com.cakii.nimble_android_challenge.data.interceptor

import android.content.Context
import com.cakii.nimble_android_challenge.R
import com.cakii.nimble_android_challenge.data.entity.Auth
import com.cakii.nimble_android_challenge.data.entity.AuthRequest
import com.cakii.nimble_android_challenge.utils.Prefs
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException
import java.net.URL


class AuthInterceptor(private val context: Context, private val prefs: Prefs): Authenticator {

    @Throws(IOException::class)
    override fun authenticate(route: Route?, response: Response?): Request? {
        return if (response?.code() == 401) {

            val client = OkHttpClient()
            val url = URL("${context.getString(R.string.base_url)}/oauth/token")

            val authRequest = AuthRequest("carlos@nimbl3.com", "antikera", "password")
            val requestJson = Gson().toJson(authRequest)

            val mediaType = MediaType.parse("application/json")
            val body: RequestBody = RequestBody.create(mediaType, requestJson)

            val request = Request.Builder()
                .url(url)
                .post(body)
                .build()

            val authResponse = client.newCall(request).execute()
            val auth = Gson().fromJson(authResponse.body()?.string(), Auth::class.java)

            prefs.auth = auth
            val httpUrl = response.request().url().newBuilder()
                .addQueryParameter("access_token", auth.accessToken)
                .build()
            response.request().newBuilder().url(httpUrl).build()
        } else {
            null
        }
    }
}