package com.cakii.nimble_android_challenge.data.di

import com.cakii.nimble_android_challenge.data.service.SurveyService
import com.cakii.nimble_android_challenge.repository.SurveyRepository
import com.cakii.nimble_android_challenge.repository.UserRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideUserRepository(remoteSource: SurveyService): UserRepository =
        UserRepository(remoteSource)

    @Provides
    @Singleton
    fun provideSurveyRepository(remoteSource: SurveyService): SurveyRepository =
        SurveyRepository(remoteSource)
}