package com.xiaoming.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.xiaoming.databinding.ActivityRecyclerViewBinding

class RecyclerViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecyclerViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecyclerViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initData()
        initListener()
    }

    private fun initListener() {

    }

    private fun initData() {

    }
}