package com.cakii.nimble_android_challenge.data.di

import android.content.Context
import com.cakii.nimble_android_challenge.App
import com.cakii.nimble_android_challenge.utils.Prefs
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(val application: App) {

    @Provides
    @Singleton
    fun provideContext(): Context = application.applicationContext

    @Provides
    @Singleton
    fun providePrefs(context: Context): Prefs = Prefs(context)

}