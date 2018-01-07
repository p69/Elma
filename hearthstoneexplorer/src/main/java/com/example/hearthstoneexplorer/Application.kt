package com.example.hearthstoneexplorer

import com.facebook.soloader.SoLoader

class Application : android.app.Application() {
    override fun onCreate() {
        super.onCreate()
        SoLoader.init(this,false)
    }
}