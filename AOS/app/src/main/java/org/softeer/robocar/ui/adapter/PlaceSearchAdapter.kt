package org.softeer.robocar.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.softeer.robocar.data.dto.placesearch.Place
import org.softeer.robocar.data.model.CarPool
import org.softeer.robocar.data.model.PlaceItem
import org.softeer.robocar.databinding.ItemPlaceBinding

class PlaceSearchAdapter(
    private val itemClickListener: ItemClickListener
): ListAdapter<Place,PlaceViewHolder>(diffCallback) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PlaceViewHolder {
        val binding = ItemPlaceBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PlaceViewHolder(binding, itemClickListener)
    }

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {

        private val diffCallback = object : DiffUtil.ItemCallback<Place>() {
            override fun areItemsTheSame(oldItem: Place, newItem: Place): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Place, newItem: Place): Boolean {
                return oldItem == newItem
            }
        }
    }
}

class PlaceViewHolder(
    private val binding: ItemPlaceBinding,
    private val itemClickListener: ItemClickListener
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(place: Place) {
        with(binding) {
            itemTitle.text = place.place_name
            placeItem.setOnClickListener {
                val placeItem = PlaceItem(place.place_name,place.address_name)
                itemClickListener.toSelectDestination(placeItem)
            }
        }
    }
}

interface ItemClickListener {
    fun toSelectDestination(placeItem: PlaceItem)
}