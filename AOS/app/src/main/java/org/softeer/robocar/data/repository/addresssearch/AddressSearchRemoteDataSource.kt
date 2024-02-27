package org.softeer.robocar.data.repository.addresssearch

import org.softeer.robocar.data.dto.addresssearch.AddressSearchResponse

interface AddressSearchRemoteDataSource {
    suspend fun getAddressSearchResult(
        key: String,
        longitude: Double,
        latitude: Double
    ): Result<AddressSearchResponse>
}
