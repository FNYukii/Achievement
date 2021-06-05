package com.example.y.bottomnav02

import android.os.Bundle
import android.view.*
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

        //エンターキーが押されたら検索
        searchEdit.setOnKeyListener{ _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_ENTER){

                //キーボードを閉じる
//                val inputMethodManager = context?.getSystemService(Context.INPUT_METHOD_SERVICE)
//                inputMethodManager.hideSoftInputFromWindow(v.windowToken, InputMethodManager.RESULT_UNCHANGED_SHOWN)

                //検索してRecyclerView表示
                searchText = searchEdit.text.toString()
                layoutManager = GridLayoutManager(this.context, 2)
                searchRecyclerView.layoutManager = layoutManager
                adapter = CustomRecyclerViewAdapter(true, searchText)
                searchRecyclerView.adapter = this.adapter
            }
            false
        }

    }


    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }


}