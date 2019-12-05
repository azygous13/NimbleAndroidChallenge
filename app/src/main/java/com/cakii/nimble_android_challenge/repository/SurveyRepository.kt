package com.cakii.nimble_android_challenge.repository

import com.cakii.nimble_android_challenge.Mockable
import com.cakii.nimble_android_challenge.data.service.SurveyService

@Mockable
class SurveyRepository(private val remoteSource: SurveyService) {

    fun getSurveys() = remoteSource.getSurveys()
}