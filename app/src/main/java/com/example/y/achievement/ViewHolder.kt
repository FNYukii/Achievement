package com.example.y.achievement

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.one_frame.view.*

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var frameBackground: ConstraintLayout? =null
    var framePinImage: ImageView? = null
    var frameAchieveImage: ImageView? = null
    var frameTitleText: TextView? = null
    var frameDetailText: TextView? = null
    init {
        frameBackground = itemView.frameBackground
        framePinImage = itemView.framePinImage
        frameAchieveImage = itemView.frameAchieveImage
        frameTitleText = itemView.frameTitleText
        frameDetailText = itemView.frameDetailText
    }
}