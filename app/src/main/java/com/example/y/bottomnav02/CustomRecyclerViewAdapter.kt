package com.example.y.bottomnav02

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import io.realm.RealmResults
import kotlin.coroutines.coroutineContext

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

        //Realmからデータを取得して、TextViewにセット
        val achievement = rResults[position]
        holder.titleText?.text = "${achievement?.title.toString()}"
        holder.descriptionText?.text = "${achievement?.description.toString()}"

        //colorに応じて色を変更
        holder.constraintLayout?.setBackgroundResource(R.drawable.background_border_green)
        holder.titleText?.setTextColor(Color.parseColor("#C2FF7E"))
        holder.descriptionText?.setTextColor(Color.parseColor("#C2FF7E"))

        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, EditAchievementActivity::class.java)
            intent.putExtra("id", achievement?.id)
            it.context.startActivity(intent)
        }

    }




}