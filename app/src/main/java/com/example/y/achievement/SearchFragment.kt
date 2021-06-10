package com.example.y.achievement

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.realm.Realm
import io.realm.Sort
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.fragment_search.*

//Todo: アチーブメントの色やピン止めの有無で検索できるようにする
//Todo: 非推奨のメソッドの使用を避ける
//Todo: 検索バーをscreenCoverより前面に配置する

class SearchFragment : Fragment() {


    //RecyclerViewのインスタンス宣言
    private lateinit var adapter: CustomRecyclerViewAdapter
    private lateinit var layoutManager: RecyclerView.LayoutManager

    //検索文字列
    private var queryString: String = ""


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }


    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //SharedPreferencesオブジェクトを取得
        val sharedPref: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.context)

        //SharedPreferencesに保存されていたqueryStringを、searchViewのqueryにセット
        queryString = sharedPref.getString("queryString","").toString()
        searchView.setQuery(queryString, false)

        //検索してRecyclerViewを表示
        search()

        //検索バーのqueryが変化するたびに、検索を行う。queryStringは逐次SharedPreferencesに保存！
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                queryString = newText!!
                sharedPref.edit().putString("queryString", queryString).apply()
                search()
                return false
            }
            override fun onQueryTextSubmit(query: String?): Boolean {
                queryString = query!!
                sharedPref.edit().putString("queryString", queryString).apply()
                search()
                searchView.clearFocus()
                return false
            }
        })

        //検索バー以外の領域をタップすると、検索バーからフォーカスを外す！
        screenCover.setOnTouchListener { _, _ ->
            Log.d("hello", "screen touched")
            searchView.clearFocus()
            false
        }

    }


    private fun search(){
        //Realmのインスタンス取得
        val realm: Realm = Realm.getDefaultInstance()

        //検索内容に一致するデータを取得
        val realmResults = realm.where<Achievement>()
            .contains("title", queryString)
            .or()
            .contains("detail", queryString)
            .findAll()
            .sort("isPinned", Sort.DESCENDING, "id", Sort.DESCENDING)

        //RecyclerViewを更新
        layoutManager = GridLayoutManager(this.context, 2)
        searchRecyclerView.layoutManager = layoutManager
        adapter = CustomRecyclerViewAdapter(realmResults)
        searchRecyclerView.adapter = this.adapter

        //もしqueryがemptyなら、RecyclerViewを表示しない
        if(queryString.isEmpty()){
            searchRecyclerView.visibility = View.GONE
        }else{
            searchRecyclerView.visibility = View.VISIBLE
        }
    }


    override fun onStop() {
        super.onStop()
        searchView.clearFocus()
    }


}