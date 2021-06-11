package com.example.y.achievement

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.PreferenceManager
import android.util.Log
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.realm.Realm
import io.realm.RealmResults
import io.realm.Sort
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.fragment_search.*

//Todo: アチーブメントの色やピン止めの有無で検索できるようにする

class SearchFragment : Fragment() {

    //Realmのインスタンス取得
    val realm: Realm = Realm.getDefaultInstance()

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
        val sharedPref: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

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

        //Achievementレコードを格納する変数realmResultsを宣言
        var realmResults: RealmResults<Achievement>

        //検索バーのクエリに一致するレコードを取得
        realmResults = realm.where<Achievement>()
            .contains("title", queryString)
            .or()
            .contains("detail", queryString)
            .findAll()
            .sort("isPinned", Sort.DESCENDING, "id", Sort.DESCENDING)

        //検索バーのクエリがemptyなら、取得するレコードは0件にする
        if (queryString.isEmpty()) {
            realmResults = realm.where<Achievement>()
                .equalTo("isPinned", true)
                .and()
                .equalTo("isPinned", false)
                .findAll()
        }

        //RecyclerViewを更新
        layoutManager = GridLayoutManager(this.context, 2)
        searchRecyclerView.layoutManager = layoutManager
        adapter = CustomRecyclerViewAdapter(realmResults)
        searchRecyclerView.adapter = this.adapter
    }


    override fun onStop() {
        super.onStop()
        searchView.clearFocus()
    }


    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }


}