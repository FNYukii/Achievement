package com.example.y.achievement

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenu
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_achievement.*

class AchievementFragment : Fragment() {


    //RecyclerViewのインスタンスを宣言
    private lateinit var adapter: CustomRecyclerViewAdapter
    private lateinit var layoutManager: RecyclerView.LayoutManager


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_achievement, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //フローティングボタン
        floatingButton.setOnClickListener {
            val intent = Intent(this.context, EditActivity::class.java)
            startActivity(intent)
        }

        //Todo: もしAchieveFragmentにいる時に、BottomNavigationのAchievement項目がクリックされたら、スクロールアップする。
//        val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
//            if(item.itemId == R.id.navigation_achievement){
//                nestedScrollView.fullScroll(View.FOCUS_UP)
//                Log.d("hello", "menu1 clicked")
//                return@OnNavigationItemSelectedListener true
//            }
//            false
//        }
//        nav_view.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

    }


    override fun onStart() {
        super.onStart()

        //pinRecyclerViewを表示
        layoutManager = GridLayoutManager(this.context, 2)
        pinRecyclerView.layoutManager = layoutManager
        adapter = CustomRecyclerViewAdapter(1, "")
        pinRecyclerView.adapter = this.adapter

        //mainRecyclerViewを表示
        layoutManager = GridLayoutManager(this.context, 2)
//        layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL) //今後使うかも
        mainRecyclerView.layoutManager = layoutManager
        adapter = CustomRecyclerViewAdapter(2, "")
        mainRecyclerView.adapter = this.adapter

        //もしピン止めされたアチーブメントが無いなら、mainRecyclerViewのmarginTopを0にする
        pinRecyclerView.post {
            val param = mainRecyclerView.layoutParams as ViewGroup.MarginLayoutParams
            if(pinRecyclerView.height == 0){
                param.topMargin = 0
            }else{
                param.topMargin = 64
            }
            mainRecyclerView.layoutParams = param
        }

    }


}


