package com.xiaoming.recyclerView

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xiaoming.App
import com.xiaoming.utils.UiUtil

/**
 * 处理宫格布局左右靠边，中间间距等分
 * @param width 容器宽度
 * @param itemWidth 每个item宽度
 */
class GridItemAllOffsetDecoration(
    private val width: Float,
    private val itemWidth: Float,
    val top: Float = -1f,
    val bottom: Float = -1f,
) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val mSpanCount = (parent.layoutManager as GridLayoutManager?)!!.spanCount

        val position = parent.getChildAdapterPosition(view) % mSpanCount

        // 前后item靠左右两边，其余的item平分剩余空间2128总宽度，248每个item宽度
//        val totalRemainSpace = 2128 - mSpanCount * 248
        val totalRemainSpace = width - mSpanCount * itemWidth
        outRect.left =
            UiUtil.dp2px(App.getContext(),position * (totalRemainSpace / (mSpanCount - 1) - totalRemainSpace / mSpanCount))
        if (bottom != -1f) {
            outRect.bottom = UiUtil.dp2px(App.getContext(),bottom)
        }
        if (top != -1f) {
            outRect.top = UiUtil.dp2px(App.getContext(),top)
        }
    }
}