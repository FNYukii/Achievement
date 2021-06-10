package com.example.y.achievement

import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_edit.*

//Todo: Toastの位置を調整する

class EditActivity : AppCompatActivity(), ColorDialogFragment.DialogListener, DeleteDialogFragment.DialogListener {


    //Realmのインスタンスを取得
    private var realm: Realm = Realm.getDefaultInstance()

    //変数たち
    private var achievementId = 0
    private var isAchieved = false
    private var isPinned = false
    private var colorId = 0
    private var isGarbage = false
    private var toastMessage = ""
    private var isPinChanged = false
    private var isTextChanged = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        //Intentから渡されてきたidを取得
        achievementId = intent.getIntExtra("achievementId", 0)

        //もしIntentからアチーブメントのidを受け取ったら、そのアチーブメントを編集モードとする
        if(achievementId != 0){
            val achievement = realm.where<Achievement>()
                .equalTo("id", achievementId)
                .findFirst()
            titleEdit.setText(achievement?.title)
            detailEdit.setText(achievement?.detail)
            isAchieved = achievement?.isAchieved!!
            isPinned = achievement.isPinned
            colorId = achievement.colorId
            setPinIcon()
            setColor()
        }


        //backButtonが押されたら、Activityを終了
        backButton.setOnClickListener {
            finish()
        }

        //achieveButtonが押されたら、isAchievedを切り替えて、Activityを終了
        achieveButton.setOnClickListener {
            isAchieved = !isAchieved
            finish()
        }

        //pinButtonが押されたら、isPinnedの真偽を切り替える
        pinButton.setOnClickListener {
            isPinned = !isPinned
            setPinIcon()
        }

        //colorButtonが押されたら、colorDialogを表示
        colorButton.setOnClickListener {
            val dialogFragment = ColorDialogFragment()
            dialogFragment.show(supportFragmentManager, "dialog")
        }

        //deleteButtonが押されたら、deleteDialogを表示する
        deleteButton.setOnClickListener {
            val dialogFragment = DeleteDialogFragment()
            dialogFragment.show(supportFragmentManager, "dialog")
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


    //pinButtonのアイコンを切り替える
    private fun setPinIcon(){
        if(isPinned){
            pinButton.setImageResource(R.drawable.ic_baseline_push_pin_24)
        }else{
            pinButton.setImageResource(R.drawable.ic_outline_push_pin_24)
        }
    }


    //colorDialogからcolorIDを受け取ったら、データを更新&各Viewへ色をセット
    override fun onDialogColorIdReceive(dialog: DialogFragment, colorId: Int) {
        this.colorId = colorId
        setColor()
    }


    //deleteDialogから削除命令を受け取ったら、レコードを削除
    override fun onDialogIsDeleteReceive(dialog: DialogFragment) {
        isGarbage = true
        finish()
    }


    //各Viewへ色をセット
    private fun setColor(){

        when (colorId){
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


        //タイトルか説明のどちらかが埋められているなら、レコードを追加、もしくは更新
        if(!isGarbage && (titleEdit.text.isNotEmpty() || detailEdit.text.isNotEmpty())){
            if(achievementId == 0){
                insertRecord()
            }else{
                updateRecord()
            }
        }


        //タイトルと説明どちらもempty、またはdeleteDialogから削除命令を受け取っているなら、既存のレコードを削除
        if(isGarbage || titleEdit.text.isEmpty() && detailEdit.text.isEmpty()){
            if(achievementId != 0){
                deleteRecord()
            }
        }

        //Toastでユーザーに処理内容を報告
        if(toastMessage.isNotEmpty()){
            Toast.makeText(applicationContext, toastMessage, Toast.LENGTH_SHORT).show()
        }

        //Realmの後片付け
        realm.close()
    }


    //新規レコード追加
    private fun insertRecord(){

        //新たな主キーを作成する
        var maxId = realm.where<Achievement>().max("id")?.toInt()
        if (maxId == null) maxId = 0
        val newId = maxId + 1

        //新規レコードを追加
        realm.executeTransaction {
            val achievement = realm.createObject<Achievement>(newId)
            achievement.isAchieved = isAchieved
            achievement.isPinned = isPinned
            achievement.colorId = colorId
            achievement.title = titleEdit.text.toString()
            achievement.detail = detailEdit.text.toString()
        }
        toastMessage = "アチーブメントを追加しました"
    }


    //既存レコード更新
    private fun updateRecord(){

        //該当のレコードを取得
        val achievement = realm.where<Achievement>()
            .equalTo("id", achievementId)
            .findFirst()

        //isPinnedが変更されたかどうか確認
        if(achievement?.isPinned != isPinned){
            toastMessage = if(isPinned){
                "アチーブメントをピン止めしました"
            }else{
                "アチーブメントのピン止めを解除しました"
            }
        }

        //titleまたはdetailが変更されたかどうか確認
        if(achievement?.title != titleEdit.text.toString() || achievement.detail != detailEdit.text.toString()){
            toastMessage = "アチーブメントを更新しました"
        }

        //該当のレコードのデータを更新
        realm.executeTransaction {
            achievement?.isAchieved = isAchieved
            achievement?.isPinned = isPinned
            achievement?.colorId = colorId
            achievement?.title = titleEdit.text.toString()
            achievement?.detail = detailEdit.text.toString()
        }

    }


    //既存レコード削除
    private fun deleteRecord(){

        //該当のレコードを取得
        val achievement = realm.where<Achievement>()
            .equalTo("id", achievementId)
            .findFirst()

        //レコード削除
        realm.executeTransaction {
            achievement?.deleteFromRealm()
        }
        toastMessage = "アチーブメントを削除しました"
    }


}
