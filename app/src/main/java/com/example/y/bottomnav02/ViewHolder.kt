package com.example.y.bottomnav02

import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.one_achievement.view.*

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var linearLayout: LinearLayout? =null
    var titleText: TextView? = null
    var descriptionText: TextView? = null
    init {
        linearLayout = itemView.linearLayout
        titleText = itemView.titleText
        descriptionText = itemView.descriptionText
    }
}