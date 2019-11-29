package com.cakii.nimble_android_challenge.repository

import com.cakii.nimble_android_challenge.Mockable
import com.cakii.nimble_android_challenge.data.service.Service

@Mockable
class SurveyRepository(private val remoteSource: Service) {

    fun getSurveys() = remoteSource.getSurveys()
}