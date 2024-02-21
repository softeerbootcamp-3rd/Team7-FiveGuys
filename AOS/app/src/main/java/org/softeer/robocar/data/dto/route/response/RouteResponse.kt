package org.softeer.robocar.data.dto.route.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Serializable
data class RouteResponse(
    @SerialName("host_id") val hostId: Long,
    @SerialName("car_image") val carImage: String,
    @SerialName("host_estimated_arrival_time") val hostEstimatedArrivalTime: Long,
    @SerialName("guest_estimated_arrival_time") val guestEstimatedArrivalTime: Long,
    @SerialName("car_number") val carNumber: String,
    @SerialName("car_name") val carName: String,
    @SerialName("host_nodes") val hostNodes: List<Coordinate>,
    @SerialName("guest_nodes") val guestNodes: List<Coordinate>
)

@Serializable
@Parcelize
data class Coordinate(
    @SerialName("latitude") val latitude: Double,
    @SerialName("longitude") val longitude: Double
) : Parcelable