package com.example.y.achievement

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Achievement : RealmObject() {
    @PrimaryKey
    var id: Int = 0
    var isAchieved: Boolean = false
    var achievedDate: Int = 0 //例: 20210615
    var achievedTime: Int = 0 //例: 091231
    var isPinned: Boolean = false
    var colorId: Int = 0
    var title: String = ""
    var detail: String = ""
}