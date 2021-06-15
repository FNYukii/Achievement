package com.example.y.achievement

import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.realm.Realm
import io.realm.Sort
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.one_day.view.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DayRecyclerViewAdapter(
    private val days: Array<LocalDate?>
): RecyclerView.Adapter<DayRecyclerViewAdapter.CustomViewHolder>() {


    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val dayText: TextView = itemView.dayText
        val frame01: ImageView = itemView.frame01
        val frame02: ImageView = itemView.frame02
        val frame03: ImageView = itemView.frame03
        val frame04: ImageView = itemView.frame04

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
        }

        //days[position]:LocalDateをInt型に変換。例:20210615
        val numberFormatter = DateTimeFormatter.ofPattern("yyyyMMdd")
        val currentDay: Int = days[position]?.format(numberFormatter)?.toInt() ?: 0

        //変数currentDayと達成日が一致するレコードを取得
        val realm = Realm.getDefaultInstance()
        val realmResults = realm.where<Achievement>()
            .equalTo("achievedDate", currentDay)
            .findAll()
            .sort("achievedTime", Sort.ASCENDING)

        //realmResults.sizeが1以上なら、frame01を表示
        if(realmResults.size >= 1){
            when(realmResults[0]?.colorId){
                0 -> {
                    holder.frame01.setBackgroundResource(R.drawable.background_frame_square_white)
                }
                1 -> {
                    holder.frame01.setBackgroundResource(R.drawable.background_frame_square_green)
                }
                2 -> {
                    holder.frame01.setBackgroundResource(R.drawable.background_frame_square_blue)
                }
                3 -> {
                    holder.frame01.setBackgroundResource(R.drawable.background_frame_square_purple)
                }
                4 -> {
                    holder.frame01.setBackgroundResource(R.drawable.background_frame_square_gold)
                }
            }
        }

        //realmResults.sizeが2以上なら、frame02を表示
        if(realmResults.size >= 2){
            when(realmResults[1]?.colorId){
                0 -> {
                    holder.frame02.setBackgroundResource(R.drawable.background_frame_square_white)
                }
                1 -> {
                    holder.frame02.setBackgroundResource(R.drawable.background_frame_square_green)
                }
                2 -> {
                    holder.frame02.setBackgroundResource(R.drawable.background_frame_square_blue)
                }
                3 -> {
                    holder.frame02.setBackgroundResource(R.drawable.background_frame_square_purple)
                }
                4 -> {
                    holder.frame02.setBackgroundResource(R.drawable.background_frame_square_gold)
                }
            }
        }

        //realmResults.sizeが3以上なら、frame03を表示
        if(realmResults.size >= 3){
            when(realmResults[2]?.colorId){
                0 -> {
                    holder.frame03.setBackgroundResource(R.drawable.background_frame_square_white)
                }
                1 -> {
                    holder.frame03.setBackgroundResource(R.drawable.background_frame_square_green)
                }
                2 -> {
                    holder.frame03.setBackgroundResource(R.drawable.background_frame_square_blue)
                }
                3 -> {
                    holder.frame03.setBackgroundResource(R.drawable.background_frame_square_purple)
                }
                4 -> {
                    holder.frame03.setBackgroundResource(R.drawable.background_frame_square_gold)
                }
            }
        }

        //realmResults.sizeが4以上なら、frame04を表示
        if(realmResults.size >= 4){
            when(realmResults[3]?.colorId){
                0 -> {
                    holder.frame04.setBackgroundResource(R.drawable.background_frame_square_white)
                }
                1 -> {
                    holder.frame04.setBackgroundResource(R.drawable.background_frame_square_green)
                }
                2 -> {
                    holder.frame04.setBackgroundResource(R.drawable.background_frame_square_blue)
                }
                3 -> {
                    holder.frame04.setBackgroundResource(R.drawable.background_frame_square_purple)
                }
                4 -> {
                    holder.frame04.setBackgroundResource(R.drawable.background_frame_square_gold)
                }
            }
        }

        //日付がnull以外のセルをタップすると、optionalSearchActivityへ遷移。
        holder.itemView.setOnClickListener {
            if(days[position] != null){
                val intent = Intent(it.context, OptionalSearchActivity::class.java)
                intent.putExtra("optionId", 20)

                //レコード検索に使用する、Int型の日付情報
                intent.putExtra("achievedDateNumber", days[position]?.format(numberFormatter)?.toInt())

                //OptionalSearchActivityのlabelTextに使用する、String型の日付情報
                val stringFormatter = DateTimeFormatter.ofPattern("yyyy年M月d日")
                intent.putExtra("achievedDateString", days[position]?.format(stringFormatter))

                it.context.startActivity(intent)
            }
        }

    }



}