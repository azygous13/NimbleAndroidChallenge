package com.cakii.nimble_android_challenge.presentation.di

import com.cakii.nimble_android_challenge.data.di.AppModule
import com.cakii.nimble_android_challenge.data.di.RemoteModule
import com.cakii.nimble_android_challenge.data.di.RepositoryModule
import com.cakii.nimble_android_challenge.viewmodel.SurveyViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, RemoteModule::class, RepositoryModule::class])
interface AppComponent {

    fun inject(viewModel: SurveyViewModel)
}