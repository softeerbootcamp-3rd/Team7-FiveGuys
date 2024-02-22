package org.softeer.robocar.data.mapper

import org.softeer.robocar.data.dto.carpool.request.RequestCarPoolRequest
import org.softeer.robocar.data.model.CarPool

fun CarPool.toRequestCarPool(): RequestCarPoolRequest{
    return RequestCarPoolRequest(
        carPoolId = carPoolId,
        nickname = nickname,
        countOfMale = countOfMen,
        countOfFemale = countOfWomen
    )
}