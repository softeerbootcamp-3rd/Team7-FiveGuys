package org.softeer.robocar.data.dto.placesearch

import kotlinx.serialization.Serializable

@Serializable
data class PlaceSearchResponse(
    var documents: List<Place>
)

@Serializable
data class Place(
    var place_name: String,
    var address_name: String, // 전체 지번 주소
    var road_address_name: String, // 전체 도로명 주소
    var x: String,
    var y: String,
)
