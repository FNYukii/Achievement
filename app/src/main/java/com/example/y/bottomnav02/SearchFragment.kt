package com.example.y.bottomnav02

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.realm.Realm
import io.realm.Sort
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : Fragment() {


    //RealmとかRecyclerViewとか宣言
    private lateinit var realm: Realm
    private lateinit var adapter: CustomRecyclerViewAdapter
    private lateinit var layoutManager: RecyclerView.LayoutManager

    private var searchText: String = ""



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Realmのデフォルトインスタンスを取得
        realm = Realm.getDefaultInstance()

        searchButton.setOnClickListener {
            searchText = searchEdit.text.toString()
            showRecyclerView()
        }

    }


    override fun onStart() {
        super.onStart()

        showRecyclerView()

    }


    private fun showRecyclerView(){
        //Realmでレコードを検索
        val realmResults = realm.where(Achievement::class.java).findAll().sort("id", Sort.DESCENDING)

        //RecyclerViewを表示
        layoutManager = GridLayoutManager(this.context, 2)
        searchRecyclerView.layoutManager = layoutManager
        adapter = CustomRecyclerViewAdapter(true, searchText)
        searchRecyclerView.adapter = this.adapter
    }


    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }


}