package com.xiaoming

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private var mWindow: Window? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setTranslucent()
    }

    //状态栏透明
    private fun setTranslucent() {
        mWindow = window
        val view: View? = mWindow?.decorView
        view?.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        mWindow?.statusBarColor = Color.TRANSPARENT
        //导航栏透明
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            mWindow?.isNavigationBarContrastEnforced = false
        }
        mWindow?.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
        mWindow?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        mWindow?.navigationBarColor = Color.TRANSPARENT
    }
}