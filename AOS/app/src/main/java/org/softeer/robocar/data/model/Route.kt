package org.softeer.robocar.data.model

import kotlinx.serialization.Serializable
import org.softeer.robocar.data.dto.route.response.Coordinate

@Serializable
data class Route(
    val hostId: Long,
    val guestId: Long,
    val carImageUrl: String,
    val hostEstimatedArrivalTime: Int,
    val guestEstimatedArrivalTime: Int,
    val vehicleRegistrationNumber: String,
    val vehicleModel: String,
    val hostNodes: List<Coordinate>,
    val guestNodes: List<Coordinate>
)