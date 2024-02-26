package org.softeer.robocar.ui.adapter

import android.widget.ImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import org.softeer.robocar.utils.convertMillsToHoursAndMinutes
import org.softeer.robocar.utils.formatMillsDurationText

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        Glide.with(imgView.context)
            .load(it)
            .fitCenter()
            .into(imgView)
    }
}

@BindingAdapter("setRouteFormattedTime")
fun setRouteFormattedTime(textView: AppCompatTextView, _minutes: Int) {
    val (hours, minutes) = convertMillsToHoursAndMinutes(_minutes)
    textView.text = formatMillsDurationText(hours, minutes)
}