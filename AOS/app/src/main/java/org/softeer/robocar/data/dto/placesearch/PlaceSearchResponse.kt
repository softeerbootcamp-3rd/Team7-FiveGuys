package org.softeer.robocar.data.dto.placesearch

data class PlaceSearchResponse(
    var documents: List<Place>
)

data class Place(
    var place_name: String,
    var address_name: String, // 전체 지번 주소
    var road_address_name: String, // 전체 도로명 주소
    var x: String,
    var y: String,
)
