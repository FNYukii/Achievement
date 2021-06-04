package com.example.y.bottomnav02

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_edit_achievement.*

class EditAchievementActivity : AppCompatActivity() {

    private lateinit var realm: Realm

    private var id: Long = 0L






    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_achievement)

        backButton.setOnClickListener {
            saveAchievement()
        }

        checkButton.setOnClickListener {
            checkAchievement()
        }

        colorButton.setOnClickListener {
            changeAchievementColor()
        }

        deleteButton.setOnClickListener {
            deleteAchievement()
        }

        realm = Realm.getDefaultInstance()

    }


    private fun saveAchievement() {

        var title: String = ""
        var description: String = ""

        title = titleEdit.text.toString()
        description = descriptionEdit.text.toString()

        if(id == 0L){
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
                achievement.title = title
                achievement.description = description
            }
        }

        finish()

    }


    private fun checkAchievement() {
        TODO("Not yet implemented")
    }


    private fun changeAchievementColor() {
        TODO("Not yet implemented")
    }


    private fun deleteAchievement() {
        TODO("Not yet implemented")
    }





}
