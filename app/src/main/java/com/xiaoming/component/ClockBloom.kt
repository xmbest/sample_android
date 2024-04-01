package com.xiaoming.component

import android.animation.Animator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import java.util.Calendar

class ClockBloom(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) :
    View(context, attrs, defStyleAttr, defStyleRes) {
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(
        context,
        attrs,
        defStyleAttr,
        0
    )

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context) : this(context, null)

    //画笔
    private val mPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var rotationAngleM: Float = 0f
    private var rotationAngleH: Float = 0f
    private var runnable:Runnable? = null
    private var x = 0f
    private var y = 0f

    init {
        runnable = Runnable{
            val m: Int = Calendar.getInstance().get(Calendar.MINUTE)
            val h: Int = Calendar.getInstance().get(Calendar.HOUR)
            Log.d("TAG", "startAnim: m = $m ,n = $h")
            rotationAngleM = m * 6f
            rotationAngleH = (h % 12) * 30f + m * 0.5f
            invalidate()
            runnable?.let { handler.postDelayed(it,1000) }
        }
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        if (x == 0f || y == 0f) {
            x = width / 2f
            y = height / 2f
        }
        drawHLine(canvas)
        drawMLine(canvas)
        drawBlockCircle(canvas)
        drawWhiteCircle(canvas)
    }


    /**
     * 画黑圆
     * */
    private fun drawBlockCircle(canvas: Canvas) {
        mPaint.setColor(Color.BLACK)
        mPaint.strokeWidth = 3f
        // 取消锯齿
        mPaint.isAntiAlias = true
        // 设置空心
        mPaint.style = Paint.Style.FILL
        canvas.drawCircle(x, y, 10f, mPaint)
    }

    /**
     * 画白圆
     * */
    private fun drawWhiteCircle(canvas: Canvas) {
        mPaint.setColor(Color.WHITE)
        mPaint.strokeWidth = 3f
        // 取消锯齿
        mPaint.isAntiAlias = true
        // 设置空心
        mPaint.style = Paint.Style.STROKE
        canvas.drawCircle(x, y, 10f, mPaint)
    }

    /**
     * 画时针
     * @param canvas
     */
    private fun drawHLine(canvas: Canvas) {
        canvas.save()
        //计算画布每次旋转的角度
        canvas.rotate(rotationAngleH, x, y)
        val stopX = x
        val stopY = y - 92
        mPaint.setColor(Color.WHITE)
        mPaint.style = Paint.Style.FILL
        mPaint.strokeWidth = 14f
        mPaint.strokeCap = Paint.Cap.ROUND
        canvas.drawLine(x, y, stopX, stopY, mPaint)
        canvas.restore()
    }

    /**
     * 画分针
     * @param canvas
     */
    private fun drawMLine(canvas: Canvas) {
        canvas.save()
        canvas.rotate(rotationAngleM, x, y)
        //计算画布每次旋转的角度
        val stopX = x
        val stopY = y - 129
        mPaint.setColor(Color.WHITE)
        mPaint.strokeWidth = 8f
        mPaint.strokeCap = Paint.Cap.ROUND
        canvas.drawLine(x, y, stopX, stopY, mPaint)
        canvas.restore()
    }

    @SuppressLint("Recycle")
    fun startAnim() {
        val m: Int = Calendar.getInstance().get(Calendar.MINUTE)
        val h: Int = Calendar.getInstance().get(Calendar.HOUR)
        Log.d("TAG", "startAnim: m = $m ,n = $h")
        val rotationM = m * 6f - 90
        val rotationH = (h % 12) * 30f + m * 0.5f - 90
        invalidate()
        val animator = ValueAnimator.ofFloat(90f)
        animator.duration = 900
        animator.addUpdateListener {
            val angle = it.animatedValue as Float
            rotationAngleM = angle + rotationM
            rotationAngleH = angle + rotationH
            invalidate()
        }
        animator.addListener(object : Animator.AnimatorListener{
            override fun onAnimationStart(animation: Animator) {

            }

            override fun onAnimationEnd(animation: Animator) {
                runnable?.let { handler.postDelayed(it,1000) }
            }

            override fun onAnimationCancel(animation: Animator) {

            }

            override fun onAnimationRepeat(animation: Animator) {

            }

        })
        animator.start()
    }
}