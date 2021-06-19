package com.example.y.achievement

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import io.realm.Realm
import io.realm.RealmResults
import io.realm.Sort
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.fragment_achievement.*

class AchievementFragment : Fragment() {


    //Realmのインスタンスを取得
    var realm: Realm = Realm.getDefaultInstance()

    //全てのアチーブメントを取得
    private val allResults: RealmResults<Achievement> = realm.where<Achievement>()
        .equalTo("isAchieved", false)
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
        pinRecyclerView.layoutManager = GridLayoutManager(this.context, 2)
        pinRecyclerView.adapter = FrameRecyclerViewAdapter(pinnedResults)

        //mainRecyclerViewを表示
        mainRecyclerView.layoutManager = GridLayoutManager(this.context, 2)
        mainRecyclerView.adapter = FrameRecyclerViewAdapter(notPinnedResults)
    }


    override fun onStart() {
        super.onStart()

        //もしピン止めされたアチーブメントが無いなら、pinRecyclerViewを表示しない
        if(pinnedResults.size == 0){
            pinRecyclerView.visibility = View.GONE
        }else{
            pinRecyclerView.visibility = View.VISIBLE
        }

        //もしアチーブメントが1件も登録されていないなら、メッセージを表示する
        if(allResults.size == 0){
            noResultText.visibility = View.VISIBLE
        }else{
            noResultText.visibility = View.GONE
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }


}


