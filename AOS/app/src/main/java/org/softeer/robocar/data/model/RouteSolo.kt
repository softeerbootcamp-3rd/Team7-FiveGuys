package org.softeer.robocar.data.model

import kotlinx.serialization.Serializable
import org.softeer.robocar.data.dto.route.response.Coordinate


@Serializable
data class RouteSolo(
    val carImageUrl: String,
    val estimatedArrivalTime: Long,
    val vehicleRegistrationNumber: String,
    val vehicleModel: String,
    val nodes: List<Coordinate>
)