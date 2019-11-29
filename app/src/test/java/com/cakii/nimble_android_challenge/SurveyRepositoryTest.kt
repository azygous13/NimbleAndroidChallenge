package com.cakii.nimble_android_challenge

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.cakii.nimble_android_challenge.data.entity.Survey
import com.cakii.nimble_android_challenge.data.service.Service
import com.cakii.nimble_android_challenge.repository.SurveyRepository
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock


class SurveyRepositoryTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    lateinit var service: Service
    lateinit var repository: SurveyRepository
    lateinit var surveys: List<Survey>

    @Before
    fun setup() {
        service = mock(Service::class.java)
        repository = SurveyRepository(service)
        surveys = listOf(
            Survey("Title1", "Desc1", "imageUrl1"),
            Survey("Title2", "Desc2", "imageUrl2"),
            Survey("Title3", "Desc3", "imageUrl3")
        )
    }

    @Test
    fun `Get Surveys - Should success`() {
        `when`(repository.getSurveys()).thenReturn(Single.just(surveys))

        repository.getSurveys().test()
            .assertValue { it.count() == 3 }
            .assertValue {
                it.first() == surveys.first()
            }
            .assertValue {
                it[1] == surveys[1]
            }
            .assertValue {
                it.last() == surveys.last()
            }
    }
}
