package org.softeer.robocar.data.mapper

import org.softeer.robocar.data.dto.route.response.RouteResponse
import org.softeer.robocar.data.model.Route

fun RouteResponse.toRoute(): Route {
    return Route(
        hostId = hostId,
        guestId = guestId,
        carImageUrl = carImage, // 'carImage'를 'carImageUrl'로 매핑
        hostEstimatedArrivalTime = hostEstimatedArrivalTime,
        guestEstimatedArrivalTime = guestEstimatedArrivalTime,
        vehicleRegistrationNumber = carNumber, // 'carNumber'를 'vehicleRegistrationNumber'로 매핑
        vehicleModel = carName,
        hostNodes = hostNodes,
        guestNodes = guestNodes
    )
}