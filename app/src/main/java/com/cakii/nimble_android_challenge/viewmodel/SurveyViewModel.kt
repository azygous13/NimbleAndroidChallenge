package com.cakii.nimble_android_challenge.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cakii.nimble_android_challenge.Mockable
import com.cakii.nimble_android_challenge.data.entity.Survey
import com.cakii.nimble_android_challenge.repository.SurveyRepository
import com.cakii.nimble_android_challenge.repository.UserRepository
import com.cakii.nimble_android_challenge.utils.Prefs
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import javax.inject.Inject

@Mockable
class SurveyViewModel : ViewModel() {

    @Inject
    lateinit var prefs: Prefs
    @Inject
    lateinit var userRepository: UserRepository
    @Inject
    lateinit var surveyRepository: SurveyRepository

    private val surveys: MutableLiveData<List<Survey>> by lazy {
        MutableLiveData<List<Survey>>().also {
            loadSurveys()
        }
    }
    private val loading = MutableLiveData<Boolean>()
    private var disposable: CompositeDisposable = CompositeDisposable()

    var currentIndex = 0

    fun getSurveys(): LiveData<List<Survey>> = surveys

    fun getCurrentSurvey(): Survey? = surveys.value?.get(currentIndex)

    fun isLoading(): LiveData<Boolean> = loading

    fun loadSurveys() {
        currentIndex = 0
        loading.value = true
        surveyRepository.getSurveys()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                { response ->
                    surveys.value = response
                    loading.value = false
                },
                { e ->
                    loading.value = false
                    if (e is HttpException) {
                        val statusCode = e.response().code()
                        if (statusCode == 401) {
                            retry()
                        }
                    }
                })
            .also {
                disposable.add(it)
            }
    }

    private fun retry() {
        loading.value = true
        userRepository.auth("carlos@nimbl3.com", "antikera", "password")
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe { response ->
                loading.value = false
                prefs.auth = response
                loadSurveys()
            }
            .also {
                disposable.add(it)
            }
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}