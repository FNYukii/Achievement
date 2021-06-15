package com.example.y.achievement

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.one_day.view.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class CalendarRecyclerViewAdapter(
    private val days: Array<LocalDate?>
): RecyclerView.Adapter<CalendarRecyclerViewAdapter.CustomViewHolder>() {


    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val dayText: TextView = itemView.dayText
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.one_day, parent, false)
        return CustomViewHolder(view)
    }


    override fun getItemCount(): Int {
        return days.size //days.sizeは35or42
    }


    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        if(days[position] == null){
            holder.dayText.text = ""
        }else{
            val formatter = DateTimeFormatter.ofPattern("d")
            holder.dayText.text = days[position]?.format(formatter)
        }

        if(days[position] == LocalDate.now()){
            holder.dayText.setTextColor(Color.WHITE)
        }

        holder.itemView.setOnClickListener {
            val formatter = DateTimeFormatter.ofPattern("yyyy-M-d")
            var message = ""
            message = if(days[position] == null){
                "This day is null"
            }else{
                days[position]?.format(formatter).toString()
            }
            Toast.makeText(it.context, message, Toast.LENGTH_SHORT).show()
        }
    }



}