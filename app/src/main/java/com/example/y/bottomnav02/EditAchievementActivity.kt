package com.example.y.bottomnav02

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_edit_achievement.*

class EditAchievementActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_achievement)

        backButton.setOnClickListener {
            finish()
        }

    }
}