package com.xm.brokendemo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.xm.brokendemo.R
import com.xm.brokendemo.widget.TempBrokenView

/**
 * User: xiaoming
 * Date: 2024/8/21
 * Time: 14:49
 * Description:
 */
class WeatherBrokenAdapter : RecyclerView.Adapter<WeatherBrokenAdapter.Holder>() {

    private val mList = ArrayList<Pair<Int,Int>>()

    fun updateData(list: List<Pair<Int,Int>>){
        mList.clear()
        mList.addAll(list)
        var min = list[0].first
        var max = list[0].second
        list.forEach {
            if (it.first < min){
                min = it.first
            }
            if (it.second > max){
                max = it.second
            }
        }
        TempBrokenView.sMaxTemp = max
        TempBrokenView.sMinTemp = min
        notifyDataSetChanged()
    }

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val brokenView:TempBrokenView = itemView.findViewById(R.id.tbr_broken)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_broken, parent, false)
        return Holder(view)
    }

    override fun getItemCount() = mList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = mList[position]
        holder.brokenView.setTemp(
            if (position == 0) null else mList[position - 1].first,if (position == 0) null else mList[position - 1].second,
            item.first,item.second,
            if (position == mList.size - 1) null else mList[position + 1].first,if (position == mList.size - 1) null else mList[position + 1].second
        )
    }
}