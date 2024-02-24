package org.softeer.robocar.data.repository.addresssearch

import org.softeer.robocar.data.dto.addresssearch.AddressSearchResponse

interface AddressSearchRepository {
    suspend fun getAddressSearchResult(
        key: String,
        query: String
    ): Result<AddressSearchResponse>
}