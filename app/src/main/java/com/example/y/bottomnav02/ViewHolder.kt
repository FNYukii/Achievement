package com.example.y.bottomnav02

import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.one_achievement.view.*

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var constraintLayout: ConstraintLayout? =null
    var titleText: TextView? = null
    var descriptionText: TextView? = null
    init {
        constraintLayout = itemView.constraintLayout
        titleText = itemView.titleText
        descriptionText = itemView.descriptionText
    }
}