package org.softeer.robocar.data.repository.PlaceSearch

import android.app.appsearch.SearchResult
import org.softeer.robocar.data.dto.placesearch.PlaceSearchResponse
import org.softeer.robocar.data.service.PlaceSearch.PlaceSearchService
import javax.inject.Inject

class PlaceSearchRemoteDataSourceImpl @Inject constructor(
    private val placeSearchService: PlaceSearchService
): PlaceSearchRemoteDataSource {

    override suspend fun getSearchResult(
        Authorization: String,
        query: String
    ): Result<PlaceSearchResponse> {
        return runCatching {
            placeSearchService.getSearchResult(Authorization,query)
        }
    }

}