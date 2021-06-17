package com.example.y.achievement

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_pager.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

class PagerFragment : Fragment() {

    //ページ数
    private val pageSize = Int.MAX_VALUE

    //一か月分の日付情報
    private lateinit var days: Array<LocalDate?>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_pager, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //HistoryFragmentからページ番号を受け取って、当月との差を計算
        val position = arguments?.getInt("position") ?: 0
        val offsetMonth: Int = 0 - (pageSize / 2 - position)

        //一ヵ月分の日付情報をDayRecyclerViewAdapterへ渡して、それをcalendarRecyclerViewのAdapterとする
        days = createDays(offsetMonth)
        calendarRecyclerView.adapter = DayRecyclerViewAdapter(days)
        calendarRecyclerView.layoutManager = GridLayoutManager(this.context, 7)

        //labelText2を更新
        labelText2.text = SimpleDateFormat("yyyy年 M月",Locale.JAPANESE).format(Date().apply { offset(month = offsetMonth) })
    }


    override fun onStart() {
        super.onStart()
        calendarRecyclerView.adapter = DayRecyclerViewAdapter(days)
    }


    private fun Date.offset(month: Int = 0) {
        time = Calendar.getInstance().apply {
            add(Calendar.MONTH, month)
        }.timeInMillis
    }


    private fun createDays(offsetMonth: Int):Array<LocalDate?>{

        //配列や変数
        val days: MutableList<LocalDate?> = arrayListOf()
        var day: LocalDate = LocalDate.now()
        day = day.plusMonths(offsetMonth.toLong())

        //当月の日数と、一日の曜日を取得
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.MONTH, offsetMonth)
        val dayOfMonth = calendar.getActualMaximum(Calendar.DATE)
        calendar.set(Calendar.DATE, 1)
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)

        //当月の1日までをnullで埋める
        for (i in 1 until dayOfWeek){
            days.add(null)
        }

        //1日から月末日まで数字を埋める
        for (i in 1..dayOfMonth){
            days.add(LocalDate.of(day.year, day.month, i))
        }

        //余った領域はnullで埋める
        if(days.size > 35){
            val filledSize = (42) - days.size
            for (i in 0 until filledSize){
                days.add(null)
            }
        }else{
            val filledSize = (35) - days.size
            for (i in 0 until filledSize){
                days.add(null)
            }
        }

        //配列daysをreturn
        return days.toTypedArray()
    }


}