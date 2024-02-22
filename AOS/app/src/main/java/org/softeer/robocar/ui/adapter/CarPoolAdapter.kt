package org.softeer.robocar.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.softeer.robocar.data.model.CarPool
import org.softeer.robocar.databinding.FragmentCarPoolItemBinding
import org.softeer.robocar.utils.convertMinutesToHoursAndMinutes
import org.softeer.robocar.utils.formatDurationText

class CarPoolAdapter(
    private val carPoolListClickListener: CarPoolListClickListener
) : ListAdapter<CarPool, CarPoolViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarPoolViewHolder {
        val binding = FragmentCarPoolItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CarPoolViewHolder(binding, carPoolListClickListener)
    }

    override fun onBindViewHolder(viewHolder: CarPoolViewHolder, position: Int) {
        viewHolder.bind(getItem(position))
    }

    companion object {

        private val diffCallback = object : DiffUtil.ItemCallback<CarPool>() {
            override fun areItemsTheSame(oldItem: CarPool, newItem: CarPool): Boolean {
                return oldItem.carPoolId == newItem.carPoolId
            }

            override fun areContentsTheSame(oldItem: CarPool, newItem: CarPool): Boolean {
                return oldItem == newItem
            }
        }
    }
}

class CarPoolViewHolder(
    private val binding: FragmentCarPoolItemBinding,
    private val carPoolListClickListener: CarPoolListClickListener
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(carPool: CarPool) {
        with(binding) {
            val (hours, minutes) = convertMinutesToHoursAndMinutes(carPool.duration)
            duration.text = formatDurationText(hours, minutes)
            startLocation.text = carPool.startLocation
            destinationLocation.text = carPool.destinationLocation
            carPoolDetailButton.setOnClickListener {
                carPoolListClickListener.onClickDetailButton(adapterPosition)
            }
            carPoolRequestButton.setOnClickListener{
                carPoolListClickListener.onClickRequestCarPoolButton(carPool)
            }
        }
    }
}