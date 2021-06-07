package com.example.y.achievement

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.realm.Realm
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : Fragment() {


    //RealmとかRecyclerViewとか宣言
    private lateinit var realm: Realm
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

        //Realmのデフォルトインスタンスを取得
        realm = Realm.getDefaultInstance()

        //検索バーの操作イベントに応じて、検索を行う
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                queryString = newText!!
                search()
                return false
            }
            override fun onQueryTextSubmit(query: String?): Boolean {
                queryString = query!!
                search()
                searchView.clearFocus()
                return false
            }
        })

        //検索バー以外の領域をタップすると、検索バーからフォーカスを外す！
        searchRecyclerViewCover.setOnTouchListener { _, _ ->
            searchView.clearFocus()
            false
        }

    }


    override fun onStart() {
        super.onStart()
        search()
    }


    private fun search(){
        layoutManager = GridLayoutManager(this.context, 2)
        searchRecyclerView.layoutManager = layoutManager
        adapter = CustomRecyclerViewAdapter(3,  queryString)
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