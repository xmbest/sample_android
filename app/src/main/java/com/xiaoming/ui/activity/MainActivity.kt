package com.xiaoming.ui.activity

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.xiaoming.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var mWindow: Window? = null
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initData()
        initListener()
    }

    private fun initListener() {
        binding.btnToRecycler.setOnClickListener{
            startActivity(Intent(this,RecyclerViewActivity::class.java))
        }
    }

    private fun initData() {
        
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