package com.example.pavelshilyagov.tryelmish

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import com.facebook.soloader.SoLoader


class LithoApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        SoLoader.init(this,false)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}