package com.example.y.bottomnav02

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

class CustomApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        //データベース初期化 Modelクラスが変更されたらDB再構成
        Realm.init(this)
        val config = RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build()
        Realm.setDefaultConfiguration(config)

    }
}