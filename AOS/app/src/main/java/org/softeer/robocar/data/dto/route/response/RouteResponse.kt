package org.softeer.robocar.data.dto.route.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Serializable
data class RouteResponse(
    @SerialName("hostId") val hostId: Long, // JSON 필드 이름에 맞게 수정
    @SerialName("guestId") val guestId: Long,
    @SerialName("carImage") val carImage: String,
    @SerialName("hostEstimatedArrivalTime") val hostEstimatedArrivalTime: Long,
    @SerialName("guestEstimatedArrivalTime") val guestEstimatedArrivalTime: Long,
    @SerialName("carNumber") val carNumber: String,
    @SerialName("carName") val carName: String,
    @SerialName("hostNodes") val hostNodes: List<Coordinate>,
    @SerialName("guestNodes") val guestNodes: List<Coordinate>
)


@Serializable
@Parcelize
data class Coordinate(
    @SerialName("latitude") val latitude: Double,
    @SerialName("longitude") val longitude: Double
) : Parcelable