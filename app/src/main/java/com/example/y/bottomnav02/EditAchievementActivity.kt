package com.example.y.bottomnav02

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_edit_achievement.*

class EditAchievementActivity : AppCompatActivity(), ColorDialogFragment.DialogListener {


    private lateinit var realm: Realm

    private var achievementId: Long = 0L
    private var colorId: Long = 0L


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_achievement)

        backButton.setOnClickListener {
            if(titleEdit.text.isNullOrEmpty() && descriptionEdit.text.isNullOrEmpty()){
                finish()
            }else{
                saveAchievement()
            }
        }

        checkButton.setOnClickListener {
            checkAchievement()
        }

        colorButton.setOnClickListener {
            openColorDialog()
        }

        deleteButton.setOnClickListener {
            deleteAchievement()
        }

        //get Realm instance
        realm = Realm.getDefaultInstance()

        //もしAchievementFragmentからidが送られてきたなら、既存のアチーブメントを検索する
        achievementId = intent.getLongExtra("achievementId", 0L)
        if(achievementId != 0L){
            val achievement = realm.where<Achievement>().equalTo("id", achievementId).findFirst()
            colorId = achievement?.colorId!!
            setAchievementColor()
            titleEdit.setText(achievement.title)
            descriptionEdit.setText(achievement.description)
        }else{
            deleteButton.visibility = View.INVISIBLE
        }

    }


    private fun setAchievementColor(){
        when (colorId){
            0L -> {
                backButton.setColorFilter(ContextCompat.getColor(this, R.color.gray))
                checkButton.setColorFilter(ContextCompat.getColor(this, R.color.gray))
                colorButton.setColorFilter(ContextCompat.getColor(this, R.color.gray))
                deleteButton.setColorFilter(ContextCompat.getColor(this, R.color.gray))
                titleEdit.setTextColor(ContextCompat.getColor(this, R.color.white))
                descriptionEdit.setTextColor(ContextCompat.getColor(this, R.color.white))
            }
            1L -> {
                backButton.setColorFilter(ContextCompat.getColor(this, R.color.green))
                checkButton.setColorFilter(ContextCompat.getColor(this, R.color.green))
                colorButton.setColorFilter(ContextCompat.getColor(this, R.color.green))
                deleteButton.setColorFilter(ContextCompat.getColor(this, R.color.green))
                titleEdit.setTextColor(ContextCompat.getColor(this, R.color.green))
                descriptionEdit.setTextColor(ContextCompat.getColor(this, R.color.green))
            }
            2L -> {
                backButton.setColorFilter(ContextCompat.getColor(this, R.color.blue))
                checkButton.setColorFilter(ContextCompat.getColor(this, R.color.blue))
                colorButton.setColorFilter(ContextCompat.getColor(this, R.color.blue))
                deleteButton.setColorFilter(ContextCompat.getColor(this, R.color.blue))
                titleEdit.setTextColor(ContextCompat.getColor(this, R.color.blue))
                descriptionEdit.setTextColor(ContextCompat.getColor(this, R.color.blue))
            }
            3L -> {
                backButton.setColorFilter(ContextCompat.getColor(this, R.color.purple))
                checkButton.setColorFilter(ContextCompat.getColor(this, R.color.purple))
                colorButton.setColorFilter(ContextCompat.getColor(this, R.color.purple))
                deleteButton.setColorFilter(ContextCompat.getColor(this, R.color.purple))
                titleEdit.setTextColor(ContextCompat.getColor(this, R.color.purple))
                descriptionEdit.setTextColor(ContextCompat.getColor(this, R.color.purple))
            }
            4L -> {
                backButton.setColorFilter(ContextCompat.getColor(this, R.color.orange))
                checkButton.setColorFilter(ContextCompat.getColor(this, R.color.orange))
                colorButton.setColorFilter(ContextCompat.getColor(this, R.color.orange))
                deleteButton.setColorFilter(ContextCompat.getColor(this, R.color.orange))
                titleEdit.setTextColor(ContextCompat.getColor(this, R.color.orange))
                descriptionEdit.setTextColor(ContextCompat.getColor(this, R.color.orange))
            }
            5L -> {
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
        if(achievementId == 0L){
            realm.executeTransaction {
                //新レコードのidを決める
                var latestId = realm.where<Achievement>().max("id")?.toLong()
                if(latestId == null){
                    latestId = 0L
                }
                val newId = latestId + 1L
                //newIdを主キーとする新レコードを作成
                val achievement = realm.createObject<Achievement>(newId)
                //データ更新
                achievement.colorId = colorId
                achievement.title = titleEdit.text.toString()
                achievement.description = descriptionEdit.text.toString()
            }
        }

        //レコード更新
        if(achievementId != 0L){
            realm.executeTransaction {
                val achievement = realm.where<Achievement>().equalTo("id", achievementId)?.findFirst()
                achievement?.colorId = colorId
                achievement?.title = titleEdit.text.toString()
                achievement?.description = descriptionEdit.text.toString()
            }
        }

        finish()
    }


    private fun checkAchievement() {
        //Todo: Achieve achievement
    }


    private fun openColorDialog() {
        //ColorDialogFragmentを表示
        val dialogFragment = ColorDialogFragment()
        dialogFragment.show(supportFragmentManager, "dialog")
    }


    override fun onDialogColorIdReceive(dialog: DialogFragment, colorId: Long) {
        this.colorId = colorId
        setAchievementColor()
    }


    private fun deleteAchievement() {
        if (achievementId != 0L){
            realm.executeTransaction {
                val achievement = realm.where<Achievement>().equalTo("id", achievementId)?.findFirst()
                achievement?.deleteFromRealm()
            }
        }
        finish()
    }


    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }




}
