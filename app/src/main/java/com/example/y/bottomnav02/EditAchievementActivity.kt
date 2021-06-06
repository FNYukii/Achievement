package com.example.y.bottomnav02

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_edit_achievement.*

class EditAchievementActivity : AppCompatActivity(), ColorDialogFragment.DialogListener {


    private lateinit var realm: Realm

    private var achievementId: Int = 0
    private var colorId: Int = 0
    private var isPinned: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_achievement)

        //backButtonが押されたら、コンテンツがある場合のみデータを保存
        backButton.setOnClickListener {
            if(titleEdit.text.isNullOrEmpty() && descriptionEdit.text.isNullOrEmpty()){
                finish()
            }else{
                saveAchievement()
            }
        }

        //checkButtonが押されたら、アチーブメントを達成とする
        checkButton.setOnClickListener {
            realm.executeTransaction {
                val achievement = realm.where<Achievement>().equalTo("id", achievementId)?.findFirst()
                achievement?.isAchieved = achievement?.isAchieved == false
            }
            finish()
        }

        //pinButtonが押されたら、isPinnedの真偽を切り替える
        pinButton.setOnClickListener {
            if(isPinned){
                isPinned = false
                pinButton.setImageResource(R.drawable.ic_outline_push_pin_24)
            }else{
                isPinned = true
                pinButton.setImageResource(R.drawable.ic_baseline_push_pin_24)
            }
        }

        //colorButtonが押されたら、colorDialogを表示
        colorButton.setOnClickListener {
            val dialogFragment = ColorDialogFragment()
            dialogFragment.show(supportFragmentManager, "dialog")
        }

        //deleteButtonが押されたら、アチーブメントを削除する
        deleteButton.setOnClickListener {
            if (achievementId != 0){
                realm.executeTransaction {
                    val achievement = realm.where<Achievement>().equalTo("id", achievementId)?.findFirst()
                    achievement?.deleteFromRealm()
                }
            }
            finish()
        }


        //もしAchievementFragmentからidが送られてきたなら、既存のアチーブメントを検索する
        realm = Realm.getDefaultInstance()
        achievementId = intent.getIntExtra("achievementId", 0)
        if(achievementId != 0){
            val achievement = realm.where<Achievement>().equalTo("id", achievementId).findFirst()
            isPinned = achievement?.isPinned!!
            if(isPinned){
                pinButton.setImageResource(R.drawable.ic_baseline_push_pin_24)
            }
            colorId = achievement.colorId
            setAchievementColor()
            titleEdit.setText(achievement.title)
            descriptionEdit.setText(achievement.description)
        }else{
            checkButton.visibility = View.INVISIBLE
        }

    }


    private fun setAchievementColor(){
        when (colorId){
            0 -> {
                backButton.setColorFilter(ContextCompat.getColor(this, R.color.gray))
                checkButton.setColorFilter(ContextCompat.getColor(this, R.color.gray))
                pinButton.setColorFilter(ContextCompat.getColor(this, R.color.gray))
                colorButton.setColorFilter(ContextCompat.getColor(this, R.color.gray))
                deleteButton.setColorFilter(ContextCompat.getColor(this, R.color.gray))
                titleEdit.setTextColor(ContextCompat.getColor(this, R.color.white))
                descriptionEdit.setTextColor(ContextCompat.getColor(this, R.color.white))
            }
            1 -> {
                backButton.setColorFilter(ContextCompat.getColor(this, R.color.green))
                checkButton.setColorFilter(ContextCompat.getColor(this, R.color.green))
                pinButton.setColorFilter(ContextCompat.getColor(this, R.color.green))
                colorButton.setColorFilter(ContextCompat.getColor(this, R.color.green))
                deleteButton.setColorFilter(ContextCompat.getColor(this, R.color.green))
                titleEdit.setTextColor(ContextCompat.getColor(this, R.color.green))
                descriptionEdit.setTextColor(ContextCompat.getColor(this, R.color.green))
            }
            2 -> {
                backButton.setColorFilter(ContextCompat.getColor(this, R.color.blue))
                checkButton.setColorFilter(ContextCompat.getColor(this, R.color.blue))
                pinButton.setColorFilter(ContextCompat.getColor(this, R.color.blue))
                colorButton.setColorFilter(ContextCompat.getColor(this, R.color.blue))
                deleteButton.setColorFilter(ContextCompat.getColor(this, R.color.blue))
                titleEdit.setTextColor(ContextCompat.getColor(this, R.color.blue))
                descriptionEdit.setTextColor(ContextCompat.getColor(this, R.color.blue))
            }
            3 -> {
                backButton.setColorFilter(ContextCompat.getColor(this, R.color.purple))
                checkButton.setColorFilter(ContextCompat.getColor(this, R.color.purple))
                colorButton.setColorFilter(ContextCompat.getColor(this, R.color.purple))
                deleteButton.setColorFilter(ContextCompat.getColor(this, R.color.purple))
                titleEdit.setTextColor(ContextCompat.getColor(this, R.color.purple))
                descriptionEdit.setTextColor(ContextCompat.getColor(this, R.color.purple))
            }
            4 -> {
                backButton.setColorFilter(ContextCompat.getColor(this, R.color.orange))
                checkButton.setColorFilter(ContextCompat.getColor(this, R.color.orange))
                colorButton.setColorFilter(ContextCompat.getColor(this, R.color.orange))
                deleteButton.setColorFilter(ContextCompat.getColor(this, R.color.orange))
                titleEdit.setTextColor(ContextCompat.getColor(this, R.color.orange))
                descriptionEdit.setTextColor(ContextCompat.getColor(this, R.color.orange))
            }
            5 -> {
                backButton.setColorFilter(ContextCompat.getColor(this, R.color.gold))
                checkButton.setColorFilter(ContextCompat.getColor(this, R.color.gold))
                colorButton.setColorFilter(ContextCompat.getColor(this, R.color.gold))
                deleteButton.setColorFilter(ContextCompat.getColor(this, R.color.gold))
                titleEdit.setTextColor(ContextCompat.getColor(this, R.color.gold))
                descriptionEdit.setTextColor(ContextCompat.getColor(this, R.color.gold))
            }
        }
    }


    private fun saveAchievement() {
        //レコード追加
        if(achievementId == 0){
            realm.executeTransaction {
                //新レコードのidを決める
                var maxId = realm.where<Achievement>().max("id")?.toInt()
                if(maxId == null){
                    maxId = 0
                }
                val newId = maxId + 1
                //newIdを主キーとする新レコードを作成
                val achievement = realm.createObject<Achievement>(newId)
                //データ更新
                achievement.isPinned = isPinned
                achievement.colorId = colorId
                achievement.title = titleEdit.text.toString()
                achievement.description = descriptionEdit.text.toString()
            }
        }
        //レコード更新
        if(achievementId != 0){
            realm.executeTransaction {
                val achievement = realm.where<Achievement>().equalTo("id", achievementId).findFirst()
                achievement?.isPinned = isPinned
                achievement?.colorId = colorId
                achievement?.title = titleEdit.text.toString()
                achievement?.description = descriptionEdit.text.toString()
            }
        }
        finish()
    }


    override fun onDialogColorIdReceive(dialog: DialogFragment, colorId: Int) {
        this.colorId = colorId
        setAchievementColor()
    }


    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }




}
