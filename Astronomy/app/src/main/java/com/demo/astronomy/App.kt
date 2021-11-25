package com.demo.astronomy

import android.app.Application
import android.content.Context

/**
 * Application class
 */
class App : Application() {

    companion object {
        lateinit var appContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        appContext = this
    }


}