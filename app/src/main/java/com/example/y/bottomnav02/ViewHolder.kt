package com.example.y.bottomnav02

import android.media.Image
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.one_card.view.*

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var cardBackground: ConstraintLayout? =null
    var cardPinImage: ImageView? = null
    var cardCheckImage: ImageView? = null
    var cardTitleText: TextView? = null
    var cardDetailText: TextView? = null
    init {
        cardBackground = itemView.cardBackground
        cardPinImage = itemView.cardPinImage
        cardCheckImage = itemView.cardCheckImage
        cardTitleText = itemView.cardTitleText
        cardDetailText = itemView.cardDetailText
    }
}