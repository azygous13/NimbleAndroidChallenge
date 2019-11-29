package com.cakii.nimble_android_challenge

import android.app.Application
import com.cakii.nimble_android_challenge.data.di.AppModule
import com.cakii.nimble_android_challenge.data.di.RemoteModule
import com.cakii.nimble_android_challenge.data.di.RepositoryModule
import com.cakii.nimble_android_challenge.presentation.di.AppComponent
import com.cakii.nimble_android_challenge.presentation.di.DaggerAppComponent


class App : Application() {

    companion object {
        lateinit var component: AppComponent
        private const val BASE_URL = "https://nimble-survey-api.herokuapp.com/"
    }

    override fun onCreate() {
        super.onCreate()
        buildDependencyGraph()
    }

    private fun buildDependencyGraph() {
        component = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .remoteModule(RemoteModule(BASE_URL))
            .repositoryModule(RepositoryModule())
            .build()
    }
}