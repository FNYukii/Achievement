package com.example.y.achievement

import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.one_day.view.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DayRecyclerViewAdapter(
    private val days: Array<LocalDate?>
): RecyclerView.Adapter<DayRecyclerViewAdapter.CustomViewHolder>() {


    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val dayText: TextView = itemView.dayText
        val frameContainer: ConstraintLayout = itemView.frameContainer
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

        //TextViewに値をセット
        if(days[position] == null){
            holder.dayText.text = ""
        }else{
            val formatter = DateTimeFormatter.ofPattern("d")
            holder.dayText.text = days[position]?.format(formatter)
        }

        //もし本日なら、dayTextを白色で表示
        if(days[position] == LocalDate.now()){
            holder.dayText.setTextColor(Color.WHITE)
        }else{
            holder.frameContainer.visibility = View.INVISIBLE
        }

        //日付がnull以外のセルをタップすると、optionalSearchActivityへ遷移。
        holder.itemView.setOnClickListener {
            if(days[position] != null){
                val intent = Intent(it.context, OptionalSearchActivity::class.java)
                intent.putExtra("optionId", 20)

                //レコード検索に使用する、Int型の日付情報
                val numberFormatter = DateTimeFormatter.ofPattern("yyyyMMdd")
                intent.putExtra("achievedDateNumber", days[position]?.format(numberFormatter)?.toInt())

                //OptionalSearchActivityのlabelTextに使用する、String型の日付情報
                val stringFormatter = DateTimeFormatter.ofPattern("yyyy年M月d日")
                intent.putExtra("achievedDateString", days[position]?.format(stringFormatter))

                it.context.startActivity(intent)
            }
        }
    }



}