package org.softeer.robocar.data.mapper

import org.softeer.robocar.data.dto.carpool.response.CarPoolInformation
import org.softeer.robocar.data.dto.carpool.response.CarPoolListResponse
import org.softeer.robocar.data.model.CarPool
import org.softeer.robocar.data.model.CarPools

fun CarPoolInformation.toCarPool(): CarPool {
    return CarPool(
        carPoolId = hostId,
        duration = estimatedTime.toUInt(),
        startLocation = hostDepartAddress,
        destinationLocation = hostDestAddress,
        nickname = hostNickname,
        expectedCharge = estimatedPrice.toUInt(),
        countOfMen = maleCount.toUInt(),
        countOfWomen = femaleCount.toUInt()
    )
}

fun CarPoolListResponse.toCarPools(): CarPools {
    val carPoolList = list.map { carPoolInformation -> carPoolInformation.toCarPool() }
    return CarPools(
        originalCharge = originalCharge,
        carPoolList = carPoolList,
    )
}