package com.cakii.nimble_android_challenge.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.cakii.nimble_android_challenge.data.entity.Auth
import com.google.gson.Gson

class Prefs(context: Context) {

    companion object {
        const val PREFS_FILENAME = "nimble_surveys"
        const val AUTH = "auth"
    }

    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_FILENAME, 0)

    var auth: Auth
        get() = Gson().fromJson(prefs.getString(AUTH, "{}"), Auth::class.java)
        set(value) = prefs.edit{ putString(AUTH, Gson().toJson(value)) }
}