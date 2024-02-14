package org.softeer.robocar.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.softeer.robocar.R
import org.softeer.robocar.data.model.CarPool

class CarPoolAdapter : RecyclerView.Adapter<CarPoolViewHolder>() {

    private val itemList: MutableList<CarPool> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarPoolViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.fragment_car_pool_item, parent, false)
        return CarPoolViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(viewHolder: CarPoolViewHolder, position: Int) {
        viewHolder.bind(itemList[position], position)
    }

}

class CarPoolViewHolder(item: View) : RecyclerView.ViewHolder(item) {

    fun bind(item: CarPool, position: Int) {
    }
}