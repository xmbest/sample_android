package com.xiaoming.ui.activity

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.xiaoming.R
import com.xiaoming.databinding.ActivityTestBinding

class TestActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTestBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityTestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initData()
        initListener()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

    }

    private fun initListener() {
        binding.image.setOnClickListener {


        }
    }

    private fun initData() {
        var buffer = ByteArray(1024 * 1024)
        val size = assets.open("more_person.json").read(buffer)
        buffer = buffer.copyInto(buffer, 0, 0, size)
        val decodeRLEBitmap =
            decodeRLEBitmap(buffer, 2171, 3000)
        binding.image.setImageBitmap(decodeRLEBitmap)
    }


    /**
     * 从 Cocor RLE 压缩数据解码为 Bitmap
     *
     * @param rleData RLE 压缩数据
     * @param width 位图宽度
     * @param height 位图高度
     * @return 解码后的 Bitmap
     */
    fun decodeRLEBitmap(rleData: ByteArray, width: Int, height: Int): Bitmap {
        val pixels = IntArray(width * height)
        var pixelIndex = 0
        var byteIndex = 0

        while (byteIndex < rleData.size) {
            val length = rleData[byteIndex++].toInt() and 0xFF
            if (length == 0) {
                // 透明像素跳过
                pixelIndex += rleData[byteIndex++].toInt() and 0xFF
            } else {
                val color = Color.rgb(
                    rleData[byteIndex++].toInt() and 0xFF,
                    rleData[byteIndex++].toInt() and 0xFF,
                    rleData[byteIndex++].toInt() and 0xFF
                )
                for (i in 0 until length) {
                    pixels[pixelIndex++] = color
                }
            }
        }

        return Bitmap.createBitmap(pixels, width, height, Bitmap.Config.ALPHA_8)
    }
}