package com.example.y.bottomnav02

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
import kotlinx.android.synthetic.main.fragment_achievement.*





class AchievementFragment : Fragment() {


    //RealmとかRecyclerViewとか宣言
    private lateinit var realm: Realm
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
            val intent = Intent(this.context, EditAchievementActivity::class.java)
            startActivity(intent)
        }

        //Realmのデフォルトインスタンスを取得
        realm = Realm.getDefaultInstance()
    }


    override fun onStart() {
        super.onStart()

        //mainRecyclerViewを表示
        layoutManager = GridLayoutManager(this.context, 2)
        mainRecyclerView.layoutManager = layoutManager
        adapter = CustomRecyclerViewAdapter(false, "")
        mainRecyclerView.adapter = this.adapter
    }


    override fun onDestroy() {
        super.onDestroy()
//        realm.close()
    }


}


