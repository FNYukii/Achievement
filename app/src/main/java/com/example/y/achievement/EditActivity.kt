package com.example.y.achievement

import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_edit.*

class EditActivity : AppCompatActivity(), ColorDialogFragment.DialogListener {


    //Realmのインスタンスを取得
    private var realm: Realm = Realm.getDefaultInstance()

    //Realmのレコードを宣言
    private lateinit var achievement: Achievement


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        //Intentから渡されてきたidを取得
        var achievementId = intent.getIntExtra("achievementId", 0)

        //もしidが渡されていないなら、新規レコード追加
        if(achievementId == 0){
            realm.executeTransaction {
                var maxId = realm.where<Achievement>().max("id")?.toInt()
                if (maxId == null) maxId = 0
                achievementId = maxId + 1
                achievement = realm.createObject(achievementId)
            }
        }

        //もしidが渡されてきていたら、既存のレコードを検索して取得
        if(achievementId != 0){
            achievement = realm.where<Achievement>().equalTo("id", achievementId).findFirst()!!
            setPinIcon()
            setColor()
            titleEdit.setText(achievement.title)
            detailEdit.setText(achievement.detail)
        }

        //backButtonが押されたら、コンテンツがある場合のみデータを保存。空白文字のみの場合も保存する。
        backButton.setOnClickListener {
            if(titleEdit.text.isNotEmpty() || detailEdit.text.isNotEmpty()){
                saveAchievement()
            }else{
                deleteAchievement()
            }
            finish()
        }

        //achieveButtonが押されたら、アチーブメントを達成とする
        achieveButton.setOnClickListener {
            realm.executeTransaction {
                achievement.isAchieved = !achievement.isAchieved
            }
            saveAchievement()
            finish()
        }

        //pinButtonが押されたら、isPinnedの真偽を切り替える
        pinButton.setOnClickListener {
            realm.executeTransaction {
                achievement.isPinned = !achievement.isPinned
            }
            setPinIcon()
        }

        //colorButtonが押されたら、colorDialogを表示
        colorButton.setOnClickListener {
            val dialogFragment = ColorDialogFragment()
            dialogFragment.show(supportFragmentManager, "dialog")
        }

        //deleteButtonが押されたら、アチーブメントを削除する
        deleteButton.setOnClickListener {
            deleteAchievement()
            finish()
        }

        //キーボードが閉じられたら、EditTextからフォーカスを外す
        constraintLayout.viewTreeObserver.addOnGlobalLayoutListener {
            val rec = Rect()
            constraintLayout.getWindowVisibleDisplayFrame(rec)
            val screenHeight = constraintLayout.rootView.height
            val keyboardHeight = screenHeight - rec.bottom
            if(keyboardHeight <= screenHeight * 0.15){
                titleEdit.clearFocus()
                detailEdit.clearFocus()
            }
        }

    }


    //レコードのデータを更新
    private fun saveAchievement() {
        realm.executeTransaction {
            achievement.title = titleEdit.text.toString()
            achievement.detail = detailEdit.text.toString()
        }
    }


    //レコードを削除
    private fun deleteAchievement(){
        realm.executeTransaction {
            achievement.deleteFromRealm()
        }
    }


    //pinButtonのアイコンを切り替える
    private fun setPinIcon(){
        if(achievement.isPinned){
            pinButton.setImageResource(R.drawable.ic_baseline_push_pin_24)
        }else{
            pinButton.setImageResource(R.drawable.ic_outline_push_pin_24)
        }
    }


    //colorIdをRealmに保存し、各Viewへ色をセット
    override fun onDialogColorIdReceive(dialog: DialogFragment, colorId: Int) {
        realm.executeTransaction {
            achievement.colorId = colorId
        }
        setColor()
    }


    //各Viewへ色をセット
    private fun setColor(){
        when (achievement.colorId){
            0 -> {
                backButton.setColorFilter(ContextCompat.getColor(this, R.color.gray))
                achieveButton.setColorFilter(ContextCompat.getColor(this, R.color.gray))
                pinButton.setColorFilter(ContextCompat.getColor(this, R.color.gray))
                colorButton.setColorFilter(ContextCompat.getColor(this, R.color.gray))
                deleteButton.setColorFilter(ContextCompat.getColor(this, R.color.gray))
                titleEdit.setTextColor(ContextCompat.getColor(this, R.color.white))
                detailEdit.setTextColor(ContextCompat.getColor(this, R.color.white))
            }
            1 -> {
                backButton.setColorFilter(ContextCompat.getColor(this, R.color.green))
                achieveButton.setColorFilter(ContextCompat.getColor(this, R.color.green))
                pinButton.setColorFilter(ContextCompat.getColor(this, R.color.green))
                colorButton.setColorFilter(ContextCompat.getColor(this, R.color.green))
                deleteButton.setColorFilter(ContextCompat.getColor(this, R.color.green))
                titleEdit.setTextColor(ContextCompat.getColor(this, R.color.green))
                detailEdit.setTextColor(ContextCompat.getColor(this, R.color.green))
            }
            2 -> {
                backButton.setColorFilter(ContextCompat.getColor(this, R.color.blue))
                achieveButton.setColorFilter(ContextCompat.getColor(this, R.color.blue))
                pinButton.setColorFilter(ContextCompat.getColor(this, R.color.blue))
                colorButton.setColorFilter(ContextCompat.getColor(this, R.color.blue))
                deleteButton.setColorFilter(ContextCompat.getColor(this, R.color.blue))
                titleEdit.setTextColor(ContextCompat.getColor(this, R.color.blue))
                detailEdit.setTextColor(ContextCompat.getColor(this, R.color.blue))
            }
            3 -> {
                backButton.setColorFilter(ContextCompat.getColor(this, R.color.purple))
                achieveButton.setColorFilter(ContextCompat.getColor(this, R.color.purple))
                pinButton.setColorFilter(ContextCompat.getColor(this, R.color.purple))
                colorButton.setColorFilter(ContextCompat.getColor(this, R.color.purple))
                deleteButton.setColorFilter(ContextCompat.getColor(this, R.color.purple))
                titleEdit.setTextColor(ContextCompat.getColor(this, R.color.purple))
                detailEdit.setTextColor(ContextCompat.getColor(this, R.color.purple))
            }
            4 -> {
                backButton.setColorFilter(ContextCompat.getColor(this, R.color.gold))
                achieveButton.setColorFilter(ContextCompat.getColor(this, R.color.gold))
                pinButton.setColorFilter(ContextCompat.getColor(this, R.color.gold))
                colorButton.setColorFilter(ContextCompat.getColor(this, R.color.gold))
                deleteButton.setColorFilter(ContextCompat.getColor(this, R.color.gold))
                titleEdit.setTextColor(ContextCompat.getColor(this, R.color.gold))
                detailEdit.setTextColor(ContextCompat.getColor(this, R.color.gold))
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }



}
