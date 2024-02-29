package com.xiaoming.recyclerView

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

/**
 *  给recyclerView添加拖动排序功能
 * 1. itemTouchHelper.attachToRecyclerView(RecyclerView)
 * 2. Adapter -> implements ItemTouchHelperListener.ItemMoveCallback
 *   2.1 -> implement onItemMove function {
 *          //对list进行调整位置
 *          notifyItemMoved(fromPosition, toPosition);
 *      }
 */
class ItemTouchHelperListener {
    interface ItemMoveCallback {
        fun onItemMove(fromPosition: Int, toPosition: Int)
    }

    interface ItemHolderMoveCallback {
        fun onItemHolderMoveStart()
        fun onItemHolderMoveEnd()
    }

    private val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
        override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
        ): Int {
            var dragFlags = 0
//            可根据itemViewType来指定才可拖动
//            if (viewHolder.itemViewType == 1) {
            dragFlags = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
//            }
            val swipeFlags = 0
            return makeMovementFlags(dragFlags, swipeFlags)
        }

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder,
        ): Boolean {
//            可根据itemViewType来指定才可拖动
//            if (viewHolder.itemViewType == 0 || target.itemViewType == 0) {
//                return false
//            }
            //拖拽中的viewHolder的Position
            val fromPosition = viewHolder.adapterPosition
            //当前拖拽到的item的viewHolder
            val toPosition = target.adapterPosition
            if (recyclerView.adapter is ItemMoveCallback) {
                (recyclerView.adapter as ItemMoveCallback?)?.onItemMove(
                    fromPosition,
                    toPosition
                )
            }
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

        }

        override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
            super.onSelectedChanged(viewHolder, actionState)
            if (actionState !== ItemTouchHelper.ACTION_STATE_IDLE && viewHolder is ItemHolderMoveCallback) {
                (viewHolder as ItemHolderMoveCallback).onItemHolderMoveStart()
            }
        }

        override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
            super.clearView(recyclerView, viewHolder)
            if (viewHolder is ItemHolderMoveCallback) {
                (viewHolder as ItemHolderMoveCallback).onItemHolderMoveEnd()
            }
        }

        override fun isLongPressDragEnabled(): Boolean {
            return true
        }
    })
}