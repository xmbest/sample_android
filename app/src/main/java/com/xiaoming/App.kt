package com.xiaoming

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class App : Application() {
    companion object{
        @SuppressLint("StaticFieldLeak")
        private var sContext: Context? = null
        fun getContext(): Context {
            return sContext!!
        }
    }
    override fun onCreate() {
        super.onCreate()
        sContext = this
    }
}