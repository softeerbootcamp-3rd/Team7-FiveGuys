package org.softeer.robocar.ui.adapter

import org.softeer.robocar.data.model.CarPool

interface CarPoolListClickListener {
    fun onClickDetailButton(position: Int)
    fun onClickRequestCarPoolButton(carPool: CarPool)
}