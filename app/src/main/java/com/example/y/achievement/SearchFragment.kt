package com.example.y.achievement

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.preference.PreferenceManager
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

class SearchFragment : Fragment() {

    //Realmのインスタンス取得
    val realm: Realm = Realm.getDefaultInstance()

    //RecyclerViewのインスタンス宣言
    private lateinit var adapter: CustomRecyclerViewAdapter
    private lateinit var layoutManager: RecyclerView.LayoutManager

    //検索して取得するレコードを格納する変数realmResultsを宣言
    private lateinit var realmResults: RealmResults<Achievement>

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

        //検索してrealmResultsを更新
        stringSearch()

        //RecyclerViewを表示
        layoutManager = GridLayoutManager(this.context, 2)
        searchRecyclerView.layoutManager = layoutManager
        adapter = CustomRecyclerViewAdapter(realmResults)
        searchRecyclerView.adapter = this.adapter

        //検索バーのqueryが変化するたびに、検索を行う。queryStringは逐次SharedPreferencesに保存！
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                queryString = newText!!
                sharedPref.edit().putString("queryString", queryString).apply()
                stringSearch()
                return false
            }
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
                return false
            }
        })

        //検索バー以外の領域をタップすると、検索バーからフォーカスを外す！
        screenCover.setOnTouchListener { _, _ ->
            searchView.clearFocus()
            false
        }

        //未達成のアチーブメント
        searchNotAchievedOption.setOnClickListener {
            val intent = Intent(this.context, OptionalSearchActivity::class.java)
            intent.putExtra("optionId", 1)
            startActivity(intent)
        }

        //達成済みアチーブメント
        searchAchievedOption.setOnClickListener {
            val intent = Intent(this.context, OptionalSearchActivity::class.java)
            intent.putExtra("optionId", 2)
            startActivity(intent)
        }

        //ピン止めされていないアチーブメント
        searchNotPinnedOption.setOnClickListener {
            val intent = Intent(this.context, OptionalSearchActivity::class.java)
            intent.putExtra("optionId", 3)
            startActivity(intent)
        }

        //ピン止めされたアチーブメント
        searchPinnedOption.setOnClickListener {
            val intent = Intent(this.context, OptionalSearchActivity::class.java)
            intent.putExtra("optionId", 4)
            startActivity(intent)
        }

        //白色アチーブメント
        searchWhiteOption.setOnClickListener {
            val intent = Intent(this.context, OptionalSearchActivity::class.java)
            intent.putExtra("optionId", 10)
            startActivity(intent)
        }

        //緑色アチーブメント
        searchGreenOption.setOnClickListener {
            val intent = Intent(this.context, OptionalSearchActivity::class.java)
            intent.putExtra("optionId", 11)
            startActivity(intent)
        }

        //青色アチーブメント
        searchBlueOption.setOnClickListener {
            val intent = Intent(this.context, OptionalSearchActivity::class.java)
            intent.putExtra("optionId", 12)
            startActivity(intent)
        }

        //紫色アチーブメント
        searchPurpleOption.setOnClickListener {
            val intent = Intent(this.context, OptionalSearchActivity::class.java)
            intent.putExtra("optionId", 13)
            startActivity(intent)
        }

        //金色アチーブメント
        searchGoldOption.setOnClickListener {
            val intent = Intent(this.context, OptionalSearchActivity::class.java)
            intent.putExtra("optionId", 14)
            startActivity(intent)
        }

    }


    private fun stringSearch(){

        //検索バーのクエリに一致するレコードを取得
        if(queryString.isNotEmpty()){
            realmResults = realm.where<Achievement>()
                .contains("title", queryString)
                .or()
                .contains("detail", queryString)
                .findAll()
                .sort("id", Sort.DESCENDING)
            if(realmResults.size == 0){
                visibilityControl(1)
            }else{
                visibilityControl(2)
            }
        }

        //検索バーのクエリがemptyなら、取得するレコードは0件にする
        if (queryString.isEmpty()) {
            realmResults = realm.where<Achievement>()
                .equalTo("isPinned", true)
                .and()
                .equalTo("isPinned", false)
                .findAll()
            visibilityControl(0)
        }

        //RecyclerViewを再表示
        adapter = CustomRecyclerViewAdapter(realmResults)
        searchRecyclerView.adapter = this.adapter

    }


    private fun visibilityControl(type: Int){
        when(type){
            0 -> {
                //0: 検索していない時。searchOptionを表示
                searchOptionContainer.visibility = View.VISIBLE
                noResultText2.visibility = View.GONE
                searchRecyclerView.visibility = View.INVISIBLE
            }
            1 -> {
                //1: 検索結果0件。メッセージを表示
                searchOptionContainer.visibility = View.GONE
                noResultText2.visibility = View.VISIBLE
                searchRecyclerView.visibility = View.INVISIBLE
            }
            2 -> {
                //2: 検索結果あり。RecyclerViewを表示
                searchOptionContainer.visibility = View.GONE
                noResultText2.visibility = View.GONE
                searchRecyclerView.visibility = View.VISIBLE
            }
        }
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