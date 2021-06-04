package com.example.y.bottomnav02

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.realm.RealmResults

class CustomRecyclerViewAdapter(): RecyclerView.Adapter<ViewHolder>() {

    //Todo: Realmを使用するため、rResultsを宣言

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.one_achievement, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 9
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.titleText?.text = "The Forester"
        holder.descriptionText?.text = "The Forestの世界で100日生き延びる"
        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, EditAchievementActivity::class.java)
            it.context.startActivity(intent)
        }
    }




}