package com.example.y.bottomnav02

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import android.widget.Toast
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


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Realmのデフォルトインスタンスを取得
        realm = Realm.getDefaultInstance()

        //searchViewがフォーカスされている時のみ、キャンセルボタンを表示する
        searchView.setOnQueryTextFocusChangeListener { _, hasFocus ->
            if(hasFocus){
                cancelButton.visibility = View.VISIBLE
                cancelButton.text = "キャンセル"

            }else{
                cancelButton.visibility = View.GONE
                cancelButton.text = ""
            }
        }

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

        //キャンセルボタンで検索バーからフォーカスを外す
        cancelButton.setOnClickListener {
            searchView.clearFocus()
        }

    }


    private fun search(){
        layoutManager = GridLayoutManager(this.context, 2)
        searchRecyclerView.layoutManager = layoutManager
        adapter = CustomRecyclerViewAdapter(3,  queryString)
        searchRecyclerView.adapter = this.adapter
    }


    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }


}