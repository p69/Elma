package com.example.pavelshilyagov.tryelmish

import android.app.Application
import com.facebook.soloader.SoLoader


class LithoApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        SoLoader.init(this,false)
    }
}