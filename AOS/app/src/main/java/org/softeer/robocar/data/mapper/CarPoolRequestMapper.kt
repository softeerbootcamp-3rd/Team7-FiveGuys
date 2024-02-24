package org.softeer.robocar.data.mapper

import org.softeer.robocar.data.dto.carpool.request.RequestCarPoolRequest
import org.softeer.robocar.data.model.CarPool

fun CarPool.toRequestCarPool(guestDestinationLocation: String): RequestCarPoolRequest{
    return RequestCarPoolRequest(
        carPoolId = carPoolId,
        countOfMale = countOfMen,
        countOfFemale = countOfWomen,
        guestDestinationLocation = guestDestinationLocation,
    )
}