package com.example.y.bottomnav02

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        //Realmからデータを取得して、TextViewにセット
        val achievement = rResults[position]
        holder.titleText?.text = "${achievement?.title.toString()}"
        holder.descriptionText?.text = "${achievement?.description.toString()}"

        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, EditAchievementActivity::class.java)
            intent.putExtra("id", achievement?.id)
            it.context.startActivity(intent)
        }

    }




}