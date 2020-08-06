package com.person.ermao.myapplication

import android.app.Application
import com.haixue.academy.tools.HxDebugToolsManager

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        HxDebugToolsManager.install(this)
    }
}