package org.softeer.robocar.data.repository.PlaceSearch

import org.softeer.robocar.data.dto.placesearch.PlaceSearchResponse

interface PlaceSearchRepository {

    suspend fun getSearchResult(
        Authorization: String,
        query: String
    ): Result<PlaceSearchResponse>

}