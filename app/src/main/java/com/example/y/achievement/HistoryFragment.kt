package com.example.y.achievement

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import kotlinx.android.synthetic.main.fragment_history.*

class HistoryFragment : Fragment() {

    //ページ数
    private val pageSize = 10

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_history, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //CalendarPagerを表示
        calendarPager.adapter = CustomPagerAdapter(this.context as FragmentActivity)
        calendarPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        calendarPager.currentItem = pageSize / 2
    }


    private inner class CustomPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int {
            return pageSize
        }
        override fun createFragment(position: Int): Fragment {
            val fragment = CalendarPageFragment()
            val bundle = Bundle()
            bundle.putInt("position", position)
            fragment.arguments = bundle
            return fragment
        }
    }


}