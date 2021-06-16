package com.example.y.achievement

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import kotlinx.android.synthetic.main.fragment_history.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

//Todo: カレンダーの表示月を、スワイプで切り替えられるようにする

class HistoryFragment : Fragment() {


    //現在カレンダーに表示している月と現在の月との差を表す変数
    private var offsetMonth = 0
    private val startPosition = Int.MAX_VALUE / 2


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_history, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //labelText2に表示月をセット
        updateDateLabel()

        //CalendarPagerを表示
        calendarPager.adapter = CustomPagerAdapter(this.context as FragmentActivity)
        calendarPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        calendarPager.currentItem = Int.MAX_VALUE / 2



        //prevButtonを押すと、前月のカレンダーを表示
        prevButton.setOnClickListener {
            offsetMonth--
//            calendarRecyclerView.adapter = DayRecyclerViewAdapter(createDays(offsetMonth))
            updateDateLabel()
        }

        //nextButtonを押すと、次の月のカレンダーを表示
        nextButton.setOnClickListener {
            offsetMonth++
//            calendarRecyclerView.adapter = DayRecyclerViewAdapter(createDays(offsetMonth))
            updateDateLabel()
        }

    }


//    override fun onStart() {
//        super.onStart()
//        //RecyclerViewを表示
////        calendarRecyclerView.adapter = DayRecyclerViewAdapter(createDays(offsetMonth))
////        calendarRecyclerView.layoutManager = GridLayoutManager(this.context, 7)
//    }


    private inner class CustomPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {

        override fun getItemCount(): Int {
            return Int.MAX_VALUE
        }

        override fun createFragment(position: Int): Fragment {
            val fragment = CalendarPageFragment()
            val bundle = Bundle()
            bundle.putInt("position", position)
            bundle.putInt("offsetMonth", offsetMonth)
            fragment.arguments = bundle
            return fragment
        }
    }


    private fun updateDateLabel() {
        labelText2.text = SimpleDateFormat("yyyy年 M月",Locale.JAPANESE).format(Date().apply { offset(month = offsetMonth) })
    }


    private fun Date.offset(month: Int = 0) {
        time = Calendar.getInstance().apply {
            add(Calendar.MONTH, month)
        }.timeInMillis
    }


//    private fun createDays(offsetMonth: Int):Array<LocalDate?>{
//
//        //配列や変数
//        val days: MutableList<LocalDate?> = arrayListOf()
//        var day: LocalDate = LocalDate.now()
//        day = day.plusMonths(offsetMonth.toLong())
//
//        //当月の日数と、一日の曜日を取得
//        val calendar = Calendar.getInstance()
//        calendar.add(Calendar.MONTH, offsetMonth)
//        val dayOfMonth = calendar.getActualMaximum(Calendar.DATE)
//        calendar.set(Calendar.DATE, 1)
//        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
//
//        //当月の1日までをnullで埋める
//        for (i in 1 until dayOfWeek){
//            days.add(null)
//        }
//
//        //1日から月末日まで数字を埋める
//        for (i in 1..dayOfMonth){
//            days.add(LocalDate.of(day.year, day.month, i))
//        }
//
//        //余った領域はnullで埋める
//        if(days.size > 35){
//            val filledSize = (42) - days.size
//            for (i in 0 until filledSize){
//                days.add(null)
//            }
//        }else{
//            val filledSize = (35) - days.size
//            for (i in 0 until filledSize){
//                days.add(null)
//            }
//        }
//
//        //配列daysをreturn
//        return days.toTypedArray()
//    }


}