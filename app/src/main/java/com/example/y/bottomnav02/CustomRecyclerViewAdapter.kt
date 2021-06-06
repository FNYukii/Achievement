package com.example.y.bottomnav02

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import io.realm.Realm
import io.realm.RealmResults
import io.realm.Sort
import io.realm.kotlin.where

class CustomRecyclerViewAdapter(
    private val filterType: Int, //1:ピン止めのみ, 2:ピン止め以外, 3:文字列で曖昧検索
    private val queryString: String
    ) : RecyclerView.Adapter<ViewHolder>() {


    //Realmから、未達成の全アチーブメントを降順で取得
    private var realm = Realm.getDefaultInstance()
    private lateinit var data: RealmResults<Achievement>


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.one_card, parent, false)
        return ViewHolder(view)
    }


    override fun getItemCount(): Int {

        //Type-1 ピン止めされたアチーブメントのみを取得
        if(filterType == 1){
            data = realm.where<Achievement>()
                .equalTo("isAchieved", false)
                .and()
                .equalTo("isPinned", true)
                .findAll()
                .sort("id", Sort.DESCENDING)
        }

        //Type-2 ピン止めされていないアチーブメントのみを取得
        if(filterType == 2){
            data = realm.where<Achievement>()
                .equalTo("isAchieved", false)
                .and()
                .equalTo("isPinned", false)
                .findAll()
                .sort("id", Sort.DESCENDING)
        }

        //Type-3 queryStringで曖昧検索
        if(filterType == 3){
            if(queryString.isEmpty()){
                return 0
            }
            data = realm.where<Achievement>()
                .contains("title", queryString)
                .or()
                .contains("description", queryString)
                .findAll()
                .sort("isPinned", Sort.DESCENDING, "id", Sort.DESCENDING)
        }

        //レコード数を返す
        return data.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        //contextを取得する
        val context: Context = holder.itemView.context

        //レコードを取得
        val achievement = data[position]

        //もしピン止めされたアチーブメントなら、ピンアイコンを表示
        if(achievement?.isPinned == true){
            holder.cardPinImage?.visibility = View.VISIBLE
        }

        //タイトルと説明をセット
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
                holder.cardPinImage?.setColorFilter(ContextCompat.getColor(context, R.color.green))
                holder.cardTitleText?.setTextColor(ContextCompat.getColor(context, R.color.green))
                holder.cardDetailText?.setTextColor(ContextCompat.getColor(context, R.color.green))
            }
            2 -> {
                holder.cardBackground?.setBackgroundResource(R.drawable.background_card_blue)
                holder.cardPinImage?.setColorFilter(ContextCompat.getColor(context, R.color.blue))
                holder.cardTitleText?.setTextColor(ContextCompat.getColor(context, R.color.blue))
                holder.cardDetailText?.setTextColor(ContextCompat.getColor(context, R.color.blue))
            }
            3 -> {
                holder.cardBackground?.setBackgroundResource(R.drawable.background_card_purple)
                holder.cardPinImage?.setColorFilter(ContextCompat.getColor(context, R.color.purple))
                holder.cardTitleText?.setTextColor(ContextCompat.getColor(context, R.color.purple))
                holder.cardDetailText?.setTextColor(ContextCompat.getColor(context, R.color.purple))
            }
            4 -> {
                holder.cardBackground?.setBackgroundResource(R.drawable.background_card_orange)
                holder.cardPinImage?.setColorFilter(ContextCompat.getColor(context, R.color.orange))
                holder.cardTitleText?.setTextColor(ContextCompat.getColor(context, R.color.orange))
                holder.cardDetailText?.setTextColor(ContextCompat.getColor(context, R.color.orange))
            }
            5 -> {
                holder.cardBackground?.setBackgroundResource(R.drawable.background_card_gold)
                holder.cardPinImage?.setColorFilter(ContextCompat.getColor(context, R.color.gold))
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