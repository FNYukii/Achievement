package com.example.y.bottomnav02

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import io.realm.Realm
import io.realm.RealmResults
import io.realm.Sort
import io.realm.kotlin.where

class CustomRecyclerViewAdapter : RecyclerView.Adapter<ViewHolder>() {


    //Realmのインスタンス作成
    private var realm = Realm.getDefaultInstance()

    //achievementテーブルの全レコードを降順で取得
    private var data: RealmResults<Achievement> = realm.where<Achievement>().equalTo("isAchieved", 0L).findAll().sort("id", Sort.DESCENDING)
//    private var data: RealmResults<Achievement> = realm.where<Achievement>().findAll().sort("id", Sort.DESCENDING)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.one_achievement, parent, false)
        return ViewHolder(view)
    }


    override fun getItemCount(): Int {
        return data.size
    }


    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        //contextを取得する
        val context: Context = holder.itemView.context

        //Realmからデータを取得して、TextViewにセット
        val achievement = data[position]
        holder.titleText?.text = achievement?.title.toString()
        holder.descriptionText?.text = achievement?.description.toString()

        //もしtitleが空なら、titleTextは非表示。marginBottomも0dpにする。
        if(holder.titleText?.text.isNullOrBlank()){
            holder.titleText?.height = 0
            val param = holder.titleText?.layoutParams as ViewGroup.MarginLayoutParams
            param.setMargins(0,0,0,0)
            holder.titleText?.layoutParams = param
        }

        //colorIdに応じて色を変更する。0:white, 1:green, 2:blue, 3:purple, 4:orange, 5:gold
        when (achievement?.colorId) {
            1L -> {
                holder.linearLayout?.setBackgroundResource(R.drawable.background_achievement_green)
                holder.titleText?.setTextColor(ContextCompat.getColor(context, R.color.green))
                holder.descriptionText?.setTextColor(ContextCompat.getColor(context, R.color.green))
            }
            2L -> {
                holder.linearLayout?.setBackgroundResource(R.drawable.background_achievement_blue)
                holder.titleText?.setTextColor(ContextCompat.getColor(context, R.color.blue))
                holder.descriptionText?.setTextColor(ContextCompat.getColor(context, R.color.blue))
            }
            3L -> {
                holder.linearLayout?.setBackgroundResource(R.drawable.background_achievement_purple)
                holder.titleText?.setTextColor(ContextCompat.getColor(context, R.color.purple))
                holder.descriptionText?.setTextColor(ContextCompat.getColor(context, R.color.purple))
            }
            4L -> {
                holder.linearLayout?.setBackgroundResource(R.drawable.background_achievement_orange)
                holder.titleText?.setTextColor(ContextCompat.getColor(context, R.color.orange))
                holder.descriptionText?.setTextColor(ContextCompat.getColor(context, R.color.orange))
            }
            5L -> {
                holder.linearLayout?.setBackgroundResource(R.drawable.background_achievement_gold)
                holder.titleText?.setTextColor(ContextCompat.getColor(context, R.color.gold))
                holder.descriptionText?.setTextColor(ContextCompat.getColor(context, R.color.gold))
            }
        }

        //EditAchievementActivityへ遷移するクリックリスナーをセット
        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, EditAchievementActivity::class.java)
            intent.putExtra("achievementId", achievement?.id)
            it.context.startActivity(intent)
        }

    }




}