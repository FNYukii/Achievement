package com.example.y.bottomnav02

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_achievement.*





class AchievementFragment : Fragment() {


    private lateinit var adapter: CustomRecyclerViewAdapter
    private lateinit var layoutManager: RecyclerView.LayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_achievement, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Click floating button to go to edit achievement
        floatingButton.setOnClickListener {
            val intent = Intent(this.context, EditAchievementActivity::class.java)
            startActivity(intent)
        }
    }


    override fun onStart() {
        super.onStart()
        //Todo: Realmからデータを取得
        layoutManager = GridLayoutManager(this.context, 2)
        recyclerView.layoutManager = layoutManager
        adapter = CustomRecyclerViewAdapter()
        recyclerView.adapter = this.adapter
    }



}


