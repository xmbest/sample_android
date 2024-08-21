package com.xm.brokendemo.widget;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.xm.brokendemo.R;


/**
 * User: xiaoming
 * Date: 2024/8/20
 * Time: 20:39
 * Description:
 */
public class TempBrokenView extends View {
    private final String TAG = "TempBrokenView";
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    /**
     * 所有天数中最大温度值
     */
    public static int sMaxTemp = 0;
    /**
     * 所有天数中最低温度值
     */
    public static int sMinTemp = 0;

    /**
     * 每度的像素长度
     */
    private static float setp = 10f;
    /**
     * 圆点半径
     */
    private static int radius = 5;
    /**
     * view宽度
     */
    private int mWidth = 0;
    /**
     * view高度
     */
    private int mHeight = 0;

    /**
     * 上一天最小温度
     */
    private Integer mPrevMinTemp;
    /**
     * 上一天最大温度
     */
    private Integer mPrevMaxTemp;
    /**
     * 当天最小温度
     */
    private int mMinTemp;
    /**
     * 当天最大温度
     */
    private int mMaxTemp;
    /**
     * 下一天最小温度
     */
    private Integer mNextMinTemp;
    /**
     * 下一天最大温度
     */
    private Integer mNextMaxTemp;

    /**
     * 是否需要draw
     */
    private boolean mNeedDraw = false;

    public TempBrokenView(Context context) {
        this(context, null);
    }

    public TempBrokenView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TempBrokenView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public TempBrokenView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mPaint.setColor(context.getResources().getColor(R.color.white));
    }


    /**
     * 设置数据
     *
     * @param prevMinTemp 前一天最低温度
     * @param prevMaxTemp 前一天最高温度
     * @param minTemp     当天最低温度
     * @param maxTemp     当天最高温度
     * @param nextMinTemp 后一天最低温度
     * @param nextMaxTemp 后一天最高温度
     */
    public void setTemp(Integer prevMinTemp, Integer prevMaxTemp, int minTemp, int maxTemp, Integer nextMinTemp, Integer nextMaxTemp) {
        this.mPrevMinTemp = prevMinTemp;
        this.mPrevMaxTemp = prevMaxTemp;
        this.mMaxTemp = maxTemp;
        this.mMinTemp = minTemp;
        this.mNextMaxTemp = nextMaxTemp;
        this.mNextMinTemp = nextMinTemp;
        mNeedDraw = true;
        invalidate();
    }


    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (!mNeedDraw) {
            return;
        }
        if (mWidth == 0 || mHeight == 0) {
            mWidth = getWidth();
            mHeight = getHeight();
        }
        Log.d(TAG,"draw mWidth = " + mWidth + ", mHeight = " + mHeight);
        if (mWidth == 0 || mHeight == 0) {
            return;
        }
        // - 4 * radius 的目的是避免画圆点被控件截断,后续所有y整体向下移动 2 * radius
        setp = (mHeight - 4 * radius) * 1f / (sMaxTemp - sMinTemp + 1);
        Log.d(TAG,"setp = " + setp);
        // 画温度最高折线
        drawLine(mPrevMaxTemp, mMaxTemp, mNextMaxTemp, canvas);
        // 画最低温度折线
        drawLine(mPrevMinTemp, mMinTemp, mNextMinTemp, canvas);
        // 画最大温度点
        drawPoint(mWidth / 2.0f, setp * (sMaxTemp - this.mMaxTemp),  canvas);
        // 画最低温度点
        drawPoint(mWidth / 2.0f, setp * (sMaxTemp - this.mMinTemp), canvas);
    }

    /**
     * 画线段
     *
     * @param y1     上一天最大/最小值
     * @param y2     当天最大/最小值
     * @param y3     下一天最大/最小值
     * @param canvas 画布
     */
    private void drawLine(Integer y1, Integer y2, Integer y3, Canvas canvas) {
        canvas.save();
        mPaint.setStrokeWidth(2f);
        // 画左部分折线
        if (y1 != null) {
            float y = (sMaxTemp - (y1 + y2) / 2.0f) * setp;
            canvas.drawLine(0, y  + 2 * radius, mWidth / 2.0f, setp * (sMaxTemp - y2) + 2 * radius, mPaint);
        }
        // 画右部分折线
        if (y3 != null) {
            float y = (sMaxTemp - (y2 + y3) / 2.0f) * setp;
            canvas.drawLine(mWidth / 2.0f, setp * (sMaxTemp - y2) + 2 * radius, mWidth, y + 2 * radius, mPaint);
        }
        canvas.restore();
    }

    /**
     * 画点
     *
     * @param cx x 坐标
     * @param cy y 坐标
     */
    private void drawPoint(float cx, float cy, Canvas canvas) {
        canvas.save();
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawCircle(cx, cy + 2 * radius, radius, mPaint);
        canvas.restore();
    }
}
