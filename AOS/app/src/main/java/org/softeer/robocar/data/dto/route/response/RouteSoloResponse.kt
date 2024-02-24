package org.softeer.robocar.data.dto.route.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RouteSoloResponse(
    @SerialName("carImage") val carImage: String,
    @SerialName("estimatedArrivalTime") val estimatedArrivalTime: Int,
    @SerialName("carNumber") val carNumber: String,
    @SerialName("carName") val carName: String,
    @SerialName("nodes") val nodes: List<Coordinate>
)
