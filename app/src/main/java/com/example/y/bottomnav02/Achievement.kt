package com.example.y.bottomnav02

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class Achievement : RealmObject() {
    @PrimaryKey
    var id: Long = 0L
    var datetime: Date = Date()
    var isAchieved: Long = 0L
    var title: String = ""
    var description: String = ""
}