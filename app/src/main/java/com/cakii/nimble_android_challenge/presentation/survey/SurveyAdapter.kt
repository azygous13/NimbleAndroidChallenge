package com.cakii.nimble_android_challenge.presentation.survey

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cakii.nimble_android_challenge.Mockable
import com.cakii.nimble_android_challenge.R
import com.cakii.nimble_android_challenge.data.entity.Survey
import kotlinx.android.synthetic.main.fragment_survey.view.*
import kotlin.properties.Delegates

@Mockable
class SurveyAdapter : RecyclerView.Adapter<SurveyAdapter.EventViewHolder>() {

    var surveys by Delegates.observable(listOf(Survey())) { _, _, _ -> notifyDataSetChanged() }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = EventViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.fragment_survey, parent, false))

    override fun getItemCount() = surveys.count()

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.view.apply {
            surveys[position].let {
                tv_title.text = it.title
                tv_description.text = it.description
                Glide.with(this).load(it.coverImageUrl).into(imv_background)
            }
        }
    }

    class EventViewHolder(val view: View) : RecyclerView.ViewHolder(view)

}