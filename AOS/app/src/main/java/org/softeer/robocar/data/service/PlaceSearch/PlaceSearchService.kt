package org.softeer.robocar.data.service.PlaceSearch

import org.softeer.robocar.data.dto.placesearch.PlaceSearchResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface PlaceSearchService {
    @GET("v2/local/search/keyword.json") // Keyword.json의 정보를 받아옴
    suspend fun getSearchResult(
        @Header("Authorization") key: String, // 카카오 REST API 인증키 [필수]
        @Query("query") query: String // 검색을 원하는 질의어 [필수]
    ): PlaceSearchResponse
}
