package org.softeer.robocar.ui.adapter

interface CarPoolListClickListener {
    fun onClickDetailButton(position: Int)
    fun onClickRequestCarPoolButton(carPoolId: Long)
}