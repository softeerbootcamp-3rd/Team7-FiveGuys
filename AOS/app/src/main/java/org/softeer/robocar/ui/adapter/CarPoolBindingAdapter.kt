package org.softeer.robocar.ui.adapter

import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
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

@BindingAdapter("setCountOfMale" , "setCountOfFemale")
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
fun setCountOfCarPoolList(textView: AppCompatTextView, availableCarPoolCount: Int){
    if(availableCarPoolCount != 0)
        textView.text = "총 ${availableCarPoolCount}대의 동승 가능한 차량을 찾았어요"
}