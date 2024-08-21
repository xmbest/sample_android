package com.xm.brokendemo

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xm.brokendemo.adapter.WeatherBrokenAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var mRecyclerView:RecyclerView
    private lateinit var mAdapter:WeatherBrokenAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initView()
        initData()
    }

    private fun initData() {
        mAdapter = WeatherBrokenAdapter()
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        mRecyclerView.layoutManager = linearLayoutManager
        mRecyclerView.adapter = mAdapter
        mAdapter.updateData(listOf(
            Pair(25,30),
            Pair(26,31),
            Pair(27,33),
            Pair(27,34),
            Pair(28,33),
            Pair(27,33),
            Pair(25,31)
        ))
    }

    private fun initView() {
        mRecyclerView = findViewById(R.id.rv_broken)
    }
}