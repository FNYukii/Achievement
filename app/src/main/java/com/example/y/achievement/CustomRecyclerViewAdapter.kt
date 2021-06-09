package com.example.y.achievement

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


    //Realmのインスタンスを取得。データを格納する変数を初期化
    private var realm = Realm.getDefaultInstance()
    private lateinit var data: RealmResults<Achievement>


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.one_frame, parent, false)
        return ViewHolder(view)
    }


    override fun getItemCount(): Int {

        //Type-1 ピン止めされた未達成アチーブメントを取得
        if(filterType == 1){
            data = realm.where<Achievement>()
//                .equalTo("isAchieved", false)
//                .and()
                .equalTo("isPinned", true)
                .findAll()
                .sort("id", Sort.DESCENDING)
        }

        //Type-2 ピン止めされていない未達成アチーブメントを取得
        if(filterType == 2){
            data = realm.where<Achievement>()
//                .equalTo("isAchieved", false)
//                .and()
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
                .contains("detail", queryString)
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

        //もしピン止めされたアチーブメントでないなら、ピンアイコンを非表示
        if(achievement?.isPinned == false){
            holder.framePinImage?.visibility = View.GONE
        }

        //もし、未達成のアチーブメントなら、達成アイコンを非表示
        if(achievement?.isAchieved == false){
            holder.frameAchieveImage?.visibility = View.GONE
        }

        //タイトルと説明をセット
        holder.frameTitleText?.text = achievement?.title.toString()
        holder.frameDetailText?.text = achievement?.detail.toString()

        //もしtitleがemptyなら、titleTextは非表示。detailTextのmarginTopも0dpにする。
        if(achievement?.title.isNullOrEmpty()){
            holder.frameTitleText?.visibility = View.GONE
            val param = holder.frameDetailText?.layoutParams as ViewGroup.MarginLayoutParams
            param.setMargins(0,0,0,0)
            holder.frameDetailText?.layoutParams = param
        }

        //colorIdに応じて色を変更する。0:white, 1:green, 2:blue, 3:purple, 4:orange, 5:gold
        when (achievement?.colorId) {
            1 -> {
                holder.frameBackground?.setBackgroundResource(R.drawable.background_frame_green)
                holder.framePinImage?.setColorFilter(ContextCompat.getColor(context, R.color.green))
                holder.frameAchieveImage?.setColorFilter(ContextCompat.getColor(context, R.color.green))
                holder.frameTitleText?.setTextColor(ContextCompat.getColor(context, R.color.green))
                holder.frameDetailText?.setTextColor(ContextCompat.getColor(context, R.color.green))
            }
            2 -> {
                holder.frameBackground?.setBackgroundResource(R.drawable.background_frame_blue)
                holder.framePinImage?.setColorFilter(ContextCompat.getColor(context, R.color.blue))
                holder.frameAchieveImage?.setColorFilter(ContextCompat.getColor(context, R.color.blue))
                holder.frameTitleText?.setTextColor(ContextCompat.getColor(context, R.color.blue))
                holder.frameDetailText?.setTextColor(ContextCompat.getColor(context, R.color.blue))
            }
            3 -> {
                holder.frameBackground?.setBackgroundResource(R.drawable.background_frame_purple)
                holder.framePinImage?.setColorFilter(ContextCompat.getColor(context, R.color.purple))
                holder.frameAchieveImage?.setColorFilter(ContextCompat.getColor(context, R.color.purple))
                holder.frameTitleText?.setTextColor(ContextCompat.getColor(context, R.color.purple))
                holder.frameDetailText?.setTextColor(ContextCompat.getColor(context, R.color.purple))
            }
            4 -> {
                holder.frameBackground?.setBackgroundResource(R.drawable.background_frame_gold)
                holder.framePinImage?.setColorFilter(ContextCompat.getColor(context, R.color.gold))
                holder.frameAchieveImage?.setColorFilter(ContextCompat.getColor(context, R.color.gold))
                holder.frameTitleText?.setTextColor(ContextCompat.getColor(context, R.color.gold))
                holder.frameDetailText?.setTextColor(ContextCompat.getColor(context, R.color.gold))
            }
        }

        //EditAchievementActivityへ遷移するクリックリスナーをセット
        holder.frameBackground?.setOnClickListener {
            val intent = Intent(it.context, EditActivity::class.java)
            intent.putExtra("achievementId", achievement?.id)
            it.context.startActivity(intent)
        }

    }


}