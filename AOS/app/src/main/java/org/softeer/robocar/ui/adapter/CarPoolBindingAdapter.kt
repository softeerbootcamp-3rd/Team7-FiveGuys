package org.softeer.robocar.ui.adapter

import android.view.View
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.airbnb.lottie.LottieAnimationView
import org.softeer.robocar.R
import org.softeer.robocar.data.model.CarPoolType
import org.softeer.robocar.data.model.CarPools
import org.softeer.robocar.data.model.TaxiType
import org.softeer.robocar.utils.convertMinutesToHoursAndMinutes
import org.softeer.robocar.utils.formatDurationText
import java.text.DecimalFormat

@BindingAdapter("setFormattedTime")
fun setFormattedTime(textView: AppCompatTextView, _minutes: Int) {
    val (hours, minutes) = convertMinutesToHoursAndMinutes(_minutes)
    textView.text = formatDurationText(hours, minutes)
}

@BindingAdapter("setExpectedCharge", "setOriginalCharge")
fun setExpectedCharge(textView: AppCompatTextView, originalCharge: Int, expectedCharge: Int) {
    val decimalFormat = DecimalFormat("#,###")

    val formattedOriginalCharge = decimalFormat.format(originalCharge)
    val formattedExpectedCharge = decimalFormat.format(expectedCharge)

    val formattedText = "기존 금액 ${formattedOriginalCharge}원 | 할인된 금액 ${formattedExpectedCharge}원"

    textView.text = formattedText
}

@BindingAdapter("setCountOfMale", "setCountOfFemale")
fun setCountOfPassenger(textView: AppCompatTextView, countOfMale: Int, countOfFemale: Int) {
    val maleText = countOfMale.let { "남성 $it 명" }
    val femaleText = countOfFemale.let { "여성 $it 명" }

    val finalText = when {
        maleText.isNotEmpty() && femaleText.isNotEmpty() -> "$maleText · $femaleText"
        maleText.isNotEmpty() -> maleText
        femaleText.isNotEmpty() -> femaleText
        else -> ""
    }

    textView.text = finalText
}

@BindingAdapter("setCountOfCarPoolList")
fun setCountOfCarPoolList(textView: AppCompatTextView, availableCarPoolCount: Int) {
    if (availableCarPoolCount != 0)
        textView.text = "총 ${availableCarPoolCount}대의 동승 가능한 차량을 찾았어요"
}

@BindingAdapter("setAnimationVisibilityAsGone")
fun setAnimationVisibilityAsGone(animationView: LottieAnimationView, carPools: CarPools?) {
    if (carPools != null)
        animationView.visibility = View.GONE
}

@BindingAdapter("setTaxiTypeRadioButton")
fun setTaxiTypeRadioButton(button: AppCompatImageButton, taxiType: TaxiType) {
    if (taxiType == TaxiType.COMPACT_TAXI) {
        if (button.id == R.id.compactTaxiButton) {
            button.setImageResource(R.drawable.img_compact_taxi)
            button.background = ContextCompat.getDrawable(button.context, R.drawable.rectangle_hyundai_blue_radius_30)
        } else if (button.id == R.id.midsizeTaxiButton) {
            button.setImageResource(R.drawable.img_midsize_taxi_inactive)
            button.background = ContextCompat.getDrawable(button.context, R.drawable.rectangle_gray_700_radius_30)
        }
    } else if (taxiType == TaxiType.MID_SIZE_TAXI) {
        if (button.id == R.id.midsizeTaxiButton) {
            button.setImageResource(R.drawable.img_midsize_taxi)
            button.background = ContextCompat.getDrawable(button.context, R.drawable.rectangle_sky_blue_pms_radius_30)
        } else if (button.id == R.id.compactTaxiButton) {
            button.setImageResource(R.drawable.img_compact_taxi_inactive)
            button.background = ContextCompat.getDrawable(button.context, R.drawable.rectangle_gray_700_radius_30)
        }
    }
}

@BindingAdapter("setCarPoolTypeRadioButton")
fun setCarPoolTypeRadioButton(button: AppCompatImageButton, carPoolType: CarPoolType) {
    if (carPoolType == CarPoolType.ALONE) {
        if (button.id == R.id.rideAloneButton) {
            button.setImageResource(R.drawable.img_alone_riding)
            button.background = ContextCompat.getDrawable(button.context, R.drawable.rectangle_navy_radius_30)
        } else if (button.id == R.id.participateCarPoolButton) {
            button.setImageResource(R.drawable.img_participate_car_pool_inactive)
            button.background = ContextCompat.getDrawable(button.context, R.drawable.rectangle_gray_700_radius_30)
        } else if(button.id == R.id.recruitCarPoolButton){
            button.setImageResource(R.drawable.img_recruit_car_pool_inactive)
            button.background = ContextCompat.getDrawable(button.context, R.drawable.rectangle_gray_700_radius_30)
        }
    } else if (carPoolType == CarPoolType.RECRUIT) {
        if (button.id == R.id.rideAloneButton) {
            button.setImageResource(R.drawable.img_alone_riding_inactive)
            button.background = ContextCompat.getDrawable(button.context, R.drawable.rectangle_gray_700_radius_30)
        } else if (button.id == R.id.participateCarPoolButton) {
            button.setImageResource(R.drawable.img_participate_car_pool_inactive)
            button.background = ContextCompat.getDrawable(button.context, R.drawable.rectangle_gray_700_radius_30)
        } else if(button.id == R.id.recruitCarPoolButton){
            button.setImageResource(R.drawable.img_recruit_car_pool)
            button.background = ContextCompat.getDrawable(button.context, R.drawable.rectangle_yellow_200_radius_30)
        }
    } else if (carPoolType == CarPoolType.JOIN) {
        if (button.id == R.id.rideAloneButton) {
            button.setImageResource(R.drawable.img_alone_riding_inactive)
            button.background = ContextCompat.getDrawable(button.context, R.drawable.rectangle_gray_700_radius_30)
        } else if (button.id == R.id.participateCarPoolButton) {
            button.setImageResource(R.drawable.img_participate_car_pool)
            button.background =
                ContextCompat.getDrawable(button.context, R.drawable.rectangle_active_blue_pms_312c_radius_30)
        } else if (button.id == R.id.recruitCarPoolButton) {
            button.setImageResource(R.drawable.img_recruit_car_pool_inactive)
            button.background = ContextCompat.getDrawable(button.context, R.drawable.rectangle_gray_700_radius_30)
        }
    }
}

@BindingAdapter("setCountOfRequestCarPoolMale")
fun setCountOfRequestCarPoolMale(textView: AppCompatTextView, countOfMale: Int) {
    val maleText = countOfMale.let { "남성 : $it 명" }
    textView.text = maleText
}

@BindingAdapter("setCountOfRequestCarPoolFemale")
fun setCountOfRequestCarPoolFemale(textView: AppCompatTextView, countOfFemale: Int) {
    val femaleText = countOfFemale.let { "여성 : $it 명" }
    textView.text = femaleText
}