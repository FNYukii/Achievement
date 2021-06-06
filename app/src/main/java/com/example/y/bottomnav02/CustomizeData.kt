package com.example.y.bottomnav02

import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where

class CustomizeData {

    //Realmのインスタンスを取得
    private var realm: Realm = Realm.getDefaultInstance()

    //お好みのテキスト
    private val contents = arrayOf(
        "XD入門者", "Adobe XDを使用して、ワイヤーフレームを1つ作成する。",
        "XD上級者", "Adobe XDを使用して、ワイヤーフレームを7つ作成する。",
        "XDの達人", "Adobe XDを使用して、ワイヤーフレームを20個作成する。",
        "規則正しい生活", "毎日決まった時間に寝て、決まった時間に起床する",
        "見習いプログラマー", "他人のソースコードを参考にして、プログラムを書く",
        "イラストレーター", "イラストを7枚製作する",
        "応用の効く技術者", "応用情報技術者試験に合格する",
        "健康的な食事", "栄養バランスが考慮された、健康的な食事を摂る",
        "You are what you eat", "栄養バランスが考慮された、健康的な食生活を14日間送る",
        "清廉潔白", "嘘をつかず、誠実に過ごす",
        "臨機応変", "どんなことにも即座に対応する",
        "一期一会", "新たに5人の友達を作る",
        "優等生", "全ての課題を提出する"
    )


    //全レコードを削除&お好みのデータを追加
    init {
        realm.executeTransaction {
            realm.where<Achievement>().findAll()?.deleteAllFromRealm()
        }
        for (i in contents.indices step 2) {
            realm.executeTransaction {
                var maxId = realm.where<Achievement>().max("id")?.toInt()
                if (maxId == null) maxId = 0
                val achievement = realm.createObject<Achievement>(maxId + 1)
                achievement.title = contents[i]
                achievement.description = contents[i + 1]
            }
        }
    }


}
