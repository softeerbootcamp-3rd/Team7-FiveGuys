package org.softeer.robocar.data.dto.addresssearch

import kotlinx.serialization.Serializable

@Serializable
data class AddressSearchResponse(
    val meta: LocalMeta,
    val documents: List<TotalAddress>
)

@Serializable
data class LocalMeta(
    val total_count: Int
)

@Serializable
data class TotalAddress(
    val address: AddressInfo,
    val road_address: RoadAddressInfo?
)

@Serializable
data class AddressInfo(
    val address_name: String,
    val region_1depth_name: String,
    val region_2depth_name: String,
    val region_3depth_name: String,
    val mountain_yn: String,
    val main_address_no: String,
    val sub_address_no: String
)

@Serializable
data class RoadAddressInfo(
    val address_name: String,
    val region_1depth_name: String,
    val region_2depth_name: String,
    val region_3depth_name: String,
    val road_name: String,
    val underground_yn: String,
    val main_building_no: String,
    val sub_building_no: String,
    val building_name: String,
    val zone_no: String
)
