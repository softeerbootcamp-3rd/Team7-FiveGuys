package org.softeer.robocar.data.service.addresssearch

import org.softeer.robocar.data.dto.addresssearch.AddressSearchResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface AddressSearchService {
    @GET("v2/local/geo/coord2address.json")
    suspend fun getAddressSearchResult(
        @Header("Authorization") key: String, // 카카오 REST API 인증키 [필수]
        @Query("x") longitude: Double, // 경도 [필수]
        @Query("y") latitude: Double, // 위도 [필수]
        @Query("input_coord") inputCoord: String = "WGS84" // 좌표 체계 [기본값: WGS84]
    ): AddressSearchResponse
}