package com.xiaoming.ui.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.xiaoming.R
import com.xiaoming.databinding.ActivityScreenSaverBinding

class ScreenSaverActivity : AppCompatActivity() {
    private lateinit var binding: ActivityScreenSaverBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityScreenSaverBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initData()
        initListener()
    }

    private fun initListener() {

    }

    private fun initData() {

    }

    override fun onResume() {
        super.onResume()
        binding.cb.startAnim()
        binding.lot.animation?.start()
    }
}