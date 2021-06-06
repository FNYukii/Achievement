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

class CustomRecyclerViewAdapter(
    private val isSearched: Boolean,
    private var queryString: String
    ) : RecyclerView.Adapter<ViewHolder>() {


    //Realmのインスタンス作成
    private var realm = Realm.getDefaultInstance()

    //未達成の全アチーブメントを降順で取得
    private lateinit var data: RealmResults<Achievement>


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.one_card, parent, false)
        return ViewHolder(view)
    }


    override fun getItemCount(): Int {

        //isSearchedがfalseなら、ピン止めされたものから先に全レコードを取得
        if(!isSearched){
            data = realm.where<Achievement>()
                .equalTo("isAchieved", false)
                .findAll()
                .sort("isPinned", Sort.DESCENDING, "id", Sort.DESCENDING)

        }

        //isSearchedがtrueなら、queryStringであいまい検索
        if(isSearched){
            if(queryString.isEmpty()){
                return 0
            }
            data = realm.where<Achievement>()
                .contains("title", queryString)
                .or()
                .contains("description", queryString)
                .findAll()
                .sort("id", Sort.DESCENDING)
        }

        //レコード数を返す
        return data.size
    }


    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        //contextを取得する
        val context: Context = holder.itemView.context

        //Realmからデータを取得して、TextViewにセット
        val achievement = data[position]
        holder.cardTitleText?.text = achievement?.title.toString()
        holder.cardDetailText?.text = achievement?.description.toString()

        //もしtitleが空なら、titleTextは非表示。detailTextのmarginTopも0dpにする。
        if(holder.cardTitleText?.text.isNullOrBlank()){
            holder.cardTitleText?.height = 0
            val param = holder.cardDetailText?.layoutParams as ViewGroup.MarginLayoutParams
            param.setMargins(0,0,0,0)
            holder.cardDetailText?.layoutParams = param
        }

        //colorIdに応じて色を変更する。0:white, 1:green, 2:blue, 3:purple, 4:orange, 5:gold
        when (achievement?.colorId) {
            1 -> {
                holder.cardBackground?.setBackgroundResource(R.drawable.background_card_green)
                holder.cardTitleText?.setTextColor(ContextCompat.getColor(context, R.color.green))
                holder.cardDetailText?.setTextColor(ContextCompat.getColor(context, R.color.green))
            }
            2 -> {
                holder.cardBackground?.setBackgroundResource(R.drawable.background_card_blue)
                holder.cardTitleText?.setTextColor(ContextCompat.getColor(context, R.color.blue))
                holder.cardDetailText?.setTextColor(ContextCompat.getColor(context, R.color.blue))
            }
            3 -> {
                holder.cardBackground?.setBackgroundResource(R.drawable.background_card_purple)
                holder.cardTitleText?.setTextColor(ContextCompat.getColor(context, R.color.purple))
                holder.cardDetailText?.setTextColor(ContextCompat.getColor(context, R.color.purple))
            }
            4 -> {
                holder.cardBackground?.setBackgroundResource(R.drawable.background_card_orange)
                holder.cardTitleText?.setTextColor(ContextCompat.getColor(context, R.color.orange))
                holder.cardDetailText?.setTextColor(ContextCompat.getColor(context, R.color.orange))
            }
            5 -> {
                holder.cardBackground?.setBackgroundResource(R.drawable.background_card_gold)
                holder.cardTitleText?.setTextColor(ContextCompat.getColor(context, R.color.gold))
                holder.cardDetailText?.setTextColor(ContextCompat.getColor(context, R.color.gold))
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