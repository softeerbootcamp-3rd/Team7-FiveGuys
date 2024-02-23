package org.softeer.robocar.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.softeer.robocar.data.dto.route.response.Coordinate


@Serializable
data class Route(
    val hostId: Long,
    val guestId: Long,
    val carImageUrl: String,
    val hostEstimatedArrivalTime: Long,
    val guestEstimatedArrivalTime: Long,
    val vehicleRegistrationNumber: String,
    val vehicleModel: String,
    val hostNodes: List<Coordinate>,
    val guestNodes: List<Coordinate>
)