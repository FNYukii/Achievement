package com.example.y.bottomnav02

import android.os.Bundle
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

        searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    searchText = newText
                }
                search()
                return false
            }
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    searchText = query
                }
                search()
                return false
            }
        })

    }


    private fun search(){
                //検索してRecyclerView表示
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