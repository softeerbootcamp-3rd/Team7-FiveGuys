package org.softeer.robocar.data.service.PlaceSearch

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface PlaceSearchService {
    @GET("v2/local/search/keyword.json") // Keyword.json의 정보를 받아옴
    fun getSearchKeyword(
        @Header("Authorization") key: String, // 카카오 REST API 인증키 [필수]
        @Query("query") query: String // 검색을 원하는 질의어 [필수]
    ): Call<PlaceSearchService>
}
