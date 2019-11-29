package com.cakii.nimble_android_challenge

import android.text.format.DateUtils
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingPolicies.setIdlingResourceTimeout
import androidx.test.espresso.IdlingPolicies.setMasterPolicyTimeout
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.cakii.nimble_android_challenge.presentation.survey.SurveyActivity
import com.cakii.nimble_android_challenge.viewmodel.SurveyViewModel
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import java.util.concurrent.TimeUnit


@RunWith(AndroidJUnit4::class)
class SurveyActivityTest {

    @get:Rule
    var activityRule: ActivityTestRule<SurveyActivity> = ActivityTestRule(
        SurveyActivity::class.java, true, true
    )

    lateinit var viewModel: SurveyViewModel

    @Before
    fun setup() {
        setMasterPolicyTimeout(60, TimeUnit.SECONDS)
        setIdlingResourceTimeout(26, TimeUnit.SECONDS)

        viewModel = mock(SurveyViewModel::class.java)
    }

    @Test
    fun survey_screen_should_display() {
        val idlingResource: IdlingResource =
            ElapsedTimeIdlingResource(DateUtils.SECOND_IN_MILLIS * 10)

        IdlingRegistry.getInstance().register(idlingResource)

        onView(withId(R.id.tv_title)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_title)).check(matches(not(withText(""))))
        onView(withId(R.id.tv_description)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_description)).check(matches(not(withText(""))))
        onView(withId(R.id.indicator)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_take_the_survey)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_take_the_survey)).check(matches(withText(R.string.take_the_survey)))

        IdlingRegistry.getInstance().unregister(idlingResource)
    }
}