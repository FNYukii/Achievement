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
import io.realm.Realm
import io.realm.RealmChangeListener
import io.realm.RealmResults
import io.realm.Sort
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.fragment_achievement.*

class AchievementFragment : Fragment() {


    //RecyclerViewのインスタンスを宣言
    private lateinit var adapter: CustomRecyclerViewAdapter
    private lateinit var layoutManager: RecyclerView.LayoutManager

    //Realmのインスタンスを取得
    var realm: Realm = Realm.getDefaultInstance()

    //全てのアチーブメントを取得
    private val allResults: RealmResults<Achievement> = realm.where<Achievement>()
        .findAll()
        .sort("id", Sort.DESCENDING)

    //ピン止めされたアチーブメントを取得
    private val pinnedResults: RealmResults<Achievement> = realm.where<Achievement>()
        .equalTo("isAchieved", false)
        .and()
        .equalTo("isPinned", true)
        .findAll()
        .sort("id", Sort.DESCENDING)

    //ピン止めされていないアチーブメントを取得
    private val notPinnedResults: RealmResults<Achievement> = realm.where<Achievement>()
        .equalTo("isAchieved", false)
        .and()
        .equalTo("isPinned", false)
        .findAll()
        .sort("id", Sort.DESCENDING)


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
        layoutManager = GridLayoutManager(this.context, 2)
        pinRecyclerView.layoutManager = layoutManager
        adapter = CustomRecyclerViewAdapter(pinnedResults)
        pinRecyclerView.adapter = this.adapter

        //mainRecyclerViewを表示
        layoutManager = GridLayoutManager(this.context, 2)
        mainRecyclerView.layoutManager = layoutManager
        adapter = CustomRecyclerViewAdapter(notPinnedResults)
        mainRecyclerView.adapter = this.adapter

        //もしピン止めされたアチーブメントが無いなら、pinRecyclerViewを表示しない
        if(pinnedResults.size == 0){
            pinRecyclerView.visibility = View.GONE
        }else{
            pinRecyclerView.visibility = View.VISIBLE
        }
        pinnedResults.addChangeListener(RealmChangeListener {
            if(pinnedResults.size == 0){
                pinRecyclerView.visibility = View.GONE
            }else{
                pinRecyclerView.visibility = View.VISIBLE
            }
        })

        //もしアチーブメントが1件も登録されていないなら、メッセージを表示する
        if(allResults.size == 0){
            messageText.visibility = View.VISIBLE
        }else{
            messageText.visibility = View.GONE
        }
        allResults.addChangeListener(RealmChangeListener {
            Log.d("hello", "allResults was changed")
            if(allResults.size == 0){
                messageText.visibility = View.VISIBLE
            }else{
                messageText.visibility = View.GONE
            }
        })

    }


}


