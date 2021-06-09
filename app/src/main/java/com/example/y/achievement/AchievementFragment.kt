package com.example.y.achievement

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.GridLayoutManager
import io.realm.Realm
import io.realm.Sort
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.fragment_achievement.*

class AchievementFragment : Fragment() {


    //RecyclerViewのインスタンスを宣言
    private lateinit var adapter: CustomRecyclerViewAdapter
    private lateinit var layoutManager: RecyclerView.LayoutManager

    //Realmのインスタンスを取得
    var realm: Realm = Realm.getDefaultInstance()


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

        //pinRecyclerViewを表示
        val realmResults1 = realm.where<Achievement>()
            .equalTo("isAchieved", false)
            .and()
            .equalTo("isPinned", true)
            .findAll()
            .sort("id", Sort.DESCENDING)
        layoutManager = GridLayoutManager(this.context, 2)
        pinRecyclerView.layoutManager = layoutManager
        adapter = CustomRecyclerViewAdapter(realmResults1)
        pinRecyclerView.adapter = this.adapter

        //mainRecyclerViewを表示
        val realmResults2 = realm.where<Achievement>()
            .equalTo("isAchieved", false)
            .and()
            .equalTo("isPinned", false)
            .findAll()
            .sort("id", Sort.DESCENDING)
        layoutManager = GridLayoutManager(this.context, 2)
        mainRecyclerView.layoutManager = layoutManager
        adapter = CustomRecyclerViewAdapter(realmResults2)
        mainRecyclerView.adapter = this.adapter

    }


    override fun onStart() {
        super.onStart()


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


