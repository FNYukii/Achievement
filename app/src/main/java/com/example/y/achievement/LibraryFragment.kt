package com.example.y.achievement

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_library.*
import java.text.SimpleDateFormat
import java.util.*

//Todo: 統計データ閲覧機能を実装する

class LibraryFragment : Fragment() {


    private var offsetMonth = 0


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_library, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //RecyclerView準備
        var adapter = CalendarRecyclerViewAdapter(createDays(offsetMonth))
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(this.context, 7)
        updateDateLabel()

        prevButton.setOnClickListener {
            offsetMonth--
            adapter = CalendarRecyclerViewAdapter(createDays(offsetMonth))
            recyclerView.adapter = adapter
            updateDateLabel()
        }

        nextButton.setOnClickListener {
            offsetMonth++
            adapter = CalendarRecyclerViewAdapter(createDays(offsetMonth))
            recyclerView.adapter = adapter
            updateDateLabel()
        }

    }


    @SuppressLint("SimpleDateFormat")
    private fun updateDateLabel() {
        dateLabel.text = SimpleDateFormat("yyyy年 M月").format(Date().apply { offset(month = offsetMonth) })
    }


    private fun Date.offset(month: Int = 0) {
        time = Calendar.getInstance().apply {
            add(Calendar.MONTH, month)
        }.timeInMillis
    }


    private fun createDays(offsetMonth: Int):Array<Int>{

        val days: MutableList<Int> = arrayListOf()
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.MONTH, offsetMonth)
        val dayOfMonth = calendar.getActualMaximum(Calendar.DATE)
        calendar.set(Calendar.DATE, 1)
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)

        //当月の1日までを0で埋める
        for (i in 1 until dayOfWeek){
            days.add(0)
        }

        //1日から月末日まで数字を埋める
        for (i in 1..dayOfMonth){
            days.add(i)
        }

        //余った領域は0で埋める
        val filledSize = (42) - days.size
        for (i in 0 until filledSize){
            days.add(0)
        }

        //配列daysをreturn
        return days.toTypedArray()
    }


}