package org.softeer.robocar.domain.usecase

import org.softeer.robocar.data.dto.placesearch.PlaceSearchResponse
import org.softeer.robocar.data.repository.PlaceSearch.PlaceSearchRepository
import javax.inject.Inject

class SearchPlaceUseCase @Inject constructor(
    private val placeSearchRepository: PlaceSearchRepository
) {
    suspend operator fun invoke(Authorization: String, query: String): Result<PlaceSearchResponse> {
        return placeSearchRepository.getSearchResult(Authorization, query)
    }
}