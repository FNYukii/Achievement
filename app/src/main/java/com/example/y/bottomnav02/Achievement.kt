package com.example.y.bottomnav02

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class Achievement : RealmObject() {
    @PrimaryKey
    var id: Int = 0
    var datetime: Date = Date()
    var isAchieved: Boolean = false
    var isPinned: Boolean = false
    var colorId: Int = 0
    var title: String = ""
    var description: String = ""
}