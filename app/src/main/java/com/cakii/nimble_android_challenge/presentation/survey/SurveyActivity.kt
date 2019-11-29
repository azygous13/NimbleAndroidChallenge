package com.cakii.nimble_android_challenge.presentation.survey

import android.os.Bundle
import android.view.View
import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager2.widget.ViewPager2
import com.cakii.nimble_android_challenge.App
import com.cakii.nimble_android_challenge.R
import com.cakii.nimble_android_challenge.viewmodel.SurveyViewModel
import kotlinx.android.synthetic.main.activity_survey.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.toolbar.view.*

class SurveyActivity : AppCompatActivity() {

    @VisibleForTesting
    private val viewModel by lazy {
        ViewModelProviders.of(this).get(SurveyViewModel::class.java).also {
            App.component.inject(it)
        }
    }
    private lateinit var adapter: SurveyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_survey)
        setupView()

        viewModel.isLoading().observe(this, Observer {
            progress_bar.visibility = if (it) View.VISIBLE else View.GONE
        })
        viewModel.getSurveys().observe(this, Observer {
            adapter.surveys = it
            viewpager.setCurrentItem(0, false)
            setTitleAndDescription()
        })
    }

    private fun setupView() {
        toolbar.imv_refresh.setOnClickListener {
            viewModel.loadSurveys()
        }

        adapter = SurveyAdapter()
        viewpager.adapter = adapter
        viewpager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                viewModel.currentIndex = position
                setTitleAndDescription()
            }
        })
        indicator.setViewPager(viewpager)
        adapter.registerAdapterDataObserver(indicator.adapterDataObserver)
    }

    private fun setTitleAndDescription() {
        viewModel.getCurrentSurvey()?.let {
            tv_title.text = it.title
            tv_description.text = it.description
        }
    }
}