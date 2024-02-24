package org.softeer.robocar.data.service.addresssearch

import org.softeer.robocar.data.dto.addresssearch.AddressSearchResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface AddressSearchService {
    @GET("v2/local/geo/coord2address.json")
    suspend fun getAddressSearchResult(
        @Header("Authorization") key: String, // 카카오 REST API 인증키 [필수]
        @Query("query") query: String // 검색을 원하는 질의어 [필수]
    ): AddressSearchResponse
}