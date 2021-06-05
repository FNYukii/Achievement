package com.example.y.bottomnav02

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import io.realm.RealmResults

class CustomRecyclerViewAdapter(realmResults: RealmResults<Achievement>): RecyclerView.Adapter<ViewHolder>() {


    private val rResults: RealmResults<Achievement> = realmResults



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.one_achievement, parent, false)

        return ViewHolder(view)
    }


    override fun getItemCount(): Int {
        return rResults.size
    }


    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        //contextを取得する
        val context: Context = holder.itemView.context

        //Realmからデータを取得して、TextViewにセット
        val achievement = rResults[position]
        holder.titleText?.text = achievement?.title.toString()
        holder.descriptionText?.text = achievement?.description.toString()

        //colorIdに応じて色を変更する。0:white, 1:green, 2:blue, 3:purple, 4:orange, 5:gold
        when (achievement?.colorId) {
            1L -> {
                holder.constraintLayout?.setBackgroundResource(R.drawable.background_achievement_green)
                holder.titleText?.setTextColor(ContextCompat.getColor(context, R.color.green))
                holder.descriptionText?.setTextColor(ContextCompat.getColor(context, R.color.green))
            }
            2L -> {
                holder.constraintLayout?.setBackgroundResource(R.drawable.background_achievement_blue)
                holder.titleText?.setTextColor(ContextCompat.getColor(context, R.color.blue))
                holder.descriptionText?.setTextColor(ContextCompat.getColor(context, R.color.blue))
            }
            3L -> {
                holder.constraintLayout?.setBackgroundResource(R.drawable.background_achievement_purple)
                holder.titleText?.setTextColor(ContextCompat.getColor(context, R.color.purple))
                holder.descriptionText?.setTextColor(ContextCompat.getColor(context, R.color.purple))
            }
            4L -> {
                holder.constraintLayout?.setBackgroundResource(R.drawable.background_achievement_orange)
                holder.titleText?.setTextColor(ContextCompat.getColor(context, R.color.orange))
                holder.descriptionText?.setTextColor(ContextCompat.getColor(context, R.color.orange))
            }
            5L -> {
                holder.constraintLayout?.setBackgroundResource(R.drawable.background_achievement_gold)
                holder.titleText?.setTextColor(ContextCompat.getColor(context, R.color.gold))
                holder.descriptionText?.setTextColor(ContextCompat.getColor(context, R.color.gold))
            }
        }




        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, EditAchievementActivity::class.java)
            intent.putExtra("achievementId", achievement?.id)
            it.context.startActivity(intent)
        }

    }




}