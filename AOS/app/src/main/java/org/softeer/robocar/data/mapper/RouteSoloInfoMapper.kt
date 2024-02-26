package org.softeer.robocar.data.mapper

import org.softeer.robocar.data.dto.route.response.RouteSoloResponse
import org.softeer.robocar.data.model.RouteSolo

fun RouteSoloResponse.toRouteSolo(): RouteSolo {
    return RouteSolo(
        carImageUrl = carImage, // 'carImage'를 'carImageUrl'로 매핑
        estimatedArrivalTime = estimatedArrivalTime,
        vehicleRegistrationNumber = carNumber, // 'carNumber'를 'vehicleRegistrationNumber'로 매핑
        vehicleModel = carName,
        nodes = nodes
    )
}