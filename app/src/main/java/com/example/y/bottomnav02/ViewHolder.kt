package com.example.y.bottomnav02

import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.one_card.view.*

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var cardBackground: ConstraintLayout? =null
    var cardTitleText: TextView? = null
    var cardDetailText: TextView? = null
    init {
        cardBackground = itemView.cardBackground
        cardTitleText = itemView.cardTitleText
        cardDetailText = itemView.cardDetailText
    }
}