package com.example.y.bottomnav02

import android.bluetooth.BluetoothA2dp
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
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
            changeAchievementColor()
        }

        deleteButton.setOnClickListener {
            deleteAchievement()
        }

        //get Realm instance
        realm = Realm.getDefaultInstance()

        //もしAchievementFragmentからidが送られてきたなら、既存のアチーブメントを表示する
        achievementId = intent.getLongExtra("achievementId", 0L)
        if(achievementId != 0L){
            val achievement = realm.where<Achievement>().equalTo("id", achievementId).findFirst()
            titleEdit.setText(achievement?.title)
            descriptionEdit.setText(achievement?.description)
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
                achievement.title = titleEdit.text.toString()
                achievement.description = descriptionEdit.text.toString()
            }
        }

        //レコード更新
        if(achievementId != 0L){
            realm.executeTransaction {
                val achievement = realm.where<Achievement>().equalTo("id", achievementId)?.findFirst()
                achievement?.title = titleEdit.text.toString()
                achievement?.description = descriptionEdit.text.toString()
            }
        }

        finish()

    }


    private fun checkAchievement() {
        //Todo
        Toast.makeText(applicationContext, "ColorId is ${colorId}", Toast.LENGTH_SHORT).show()
    }


    private fun changeAchievementColor() {
        //ColorDialogFragmentを表示
        val dialogFragment = ColorDialogFragment()
        dialogFragment.show(supportFragmentManager, "dialog")
    }


    override fun onDialogColorIdReceive(dialog: DialogFragment, id: Long) {
        this.colorId = id
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
