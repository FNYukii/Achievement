package com.example.y.achievement

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import io.realm.*
import kotlinx.android.synthetic.main.one_frame.view.*

class CustomRecyclerViewAdapter(
    private val collection: OrderedRealmCollection<Achievement>?
    ) : RealmRecyclerViewAdapter<Achievement, CustomRecyclerViewAdapter.CustomViewHolder>(collection, true) {


    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val frameBackground: ConstraintLayout = itemView.frameBackground
        val framePinImage: ImageView = itemView.framePinImage
        val frameAchieveImage: ImageView = itemView.frameAchieveImage
        val frameTitleText: TextView = itemView.frameTitleText
        val frameDetailText: TextView = itemView.frameDetailText
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.one_frame, parent, false)
        return CustomViewHolder(view)
    }


    override fun getItemCount(): Int {
        return collection?.size ?: 0
    }


    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {

        //contextを取得する
        val context: Context = holder.itemView.context

        //レコードを取得
        val achievement = collection?.get(position)

        //もしピン止めされたアチーブメントでないなら、ピンアイコンを非表示
        if(achievement?.isPinned == false){
            holder.framePinImage.visibility = View.GONE
        }

        //もし、未達成のアチーブメントなら、達成アイコンを非表示
        if(achievement?.isAchieved == false){
            holder.frameAchieveImage.visibility = View.GONE
        }

        //タイトルと説明をセット
        holder.frameTitleText.text = achievement?.title.toString()
        holder.frameDetailText.text = achievement?.detail.toString()

        //もしtitleがemptyなら、titleTextは非表示。detailTextのmarginTopも0dpにする。
        if(achievement?.title.isNullOrEmpty()){
            holder.frameTitleText.visibility = View.GONE
            val param = holder.frameDetailText.layoutParams as ViewGroup.MarginLayoutParams
            param.setMargins(0,0,0,0)
            holder.frameDetailText.layoutParams = param
        }

        //colorIdに応じて色を変更する。0:white, 1:green, 2:blue, 3:purple, 4:orange, 5:gold
        when (achievement?.colorId) {
            0 -> {
                holder.frameBackground.setBackgroundResource(R.drawable.background_frame_white)
                holder.framePinImage.setColorFilter(ContextCompat.getColor(context, R.color.white))
                holder.frameAchieveImage.setColorFilter(ContextCompat.getColor(context, R.color.white))
                holder.frameTitleText.setTextColor(ContextCompat.getColor(context, R.color.white))
                holder.frameDetailText.setTextColor(ContextCompat.getColor(context, R.color.white))
            }
            1 -> {
                holder.frameBackground.setBackgroundResource(R.drawable.background_frame_green)
                holder.framePinImage.setColorFilter(ContextCompat.getColor(context, R.color.green))
                holder.frameAchieveImage.setColorFilter(ContextCompat.getColor(context, R.color.green))
                holder.frameTitleText.setTextColor(ContextCompat.getColor(context, R.color.green))
                holder.frameDetailText.setTextColor(ContextCompat.getColor(context, R.color.green))
            }
            2 -> {
                holder.frameBackground.setBackgroundResource(R.drawable.background_frame_blue)
                holder.framePinImage.setColorFilter(ContextCompat.getColor(context, R.color.blue))
                holder.frameAchieveImage.setColorFilter(ContextCompat.getColor(context, R.color.blue))
                holder.frameTitleText.setTextColor(ContextCompat.getColor(context, R.color.blue))
                holder.frameDetailText.setTextColor(ContextCompat.getColor(context, R.color.blue))
            }
            3 -> {
                holder.frameBackground.setBackgroundResource(R.drawable.background_frame_purple)
                holder.framePinImage.setColorFilter(ContextCompat.getColor(context, R.color.purple))
                holder.frameAchieveImage.setColorFilter(ContextCompat.getColor(context, R.color.purple))
                holder.frameTitleText.setTextColor(ContextCompat.getColor(context, R.color.purple))
                holder.frameDetailText.setTextColor(ContextCompat.getColor(context, R.color.purple))
            }
            4 -> {
                holder.frameBackground.setBackgroundResource(R.drawable.background_frame_gold)
                holder.framePinImage.setColorFilter(ContextCompat.getColor(context, R.color.gold))
                holder.frameAchieveImage.setColorFilter(ContextCompat.getColor(context, R.color.gold))
                holder.frameTitleText.setTextColor(ContextCompat.getColor(context, R.color.gold))
                holder.frameDetailText.setTextColor(ContextCompat.getColor(context, R.color.gold))
            }
        }

        //EditAchievementActivityへ遷移するクリックリスナーをセット
        holder.frameBackground.setOnClickListener {
            val intent = Intent(it.context, EditActivity::class.java)
            intent.putExtra("achievementId", achievement?.id)
            it.context.startActivity(intent)
        }

    }

}