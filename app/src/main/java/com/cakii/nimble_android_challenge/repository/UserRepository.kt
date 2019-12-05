package com.cakii.nimble_android_challenge.repository

import com.cakii.nimble_android_challenge.data.service.SurveyService

class UserRepository(private val remoteSource: SurveyService) {

    fun auth(username: String, password: String, grantType: String) = remoteSource.auth(username, password, grantType)
}