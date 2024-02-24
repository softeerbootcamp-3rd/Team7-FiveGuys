package org.softeer.robocar.data.repository.addresssearch

import org.softeer.robocar.data.dto.addresssearch.AddressSearchResponse
import javax.inject.Inject

class AddressSearchRepositoryImpl @Inject constructor(
    private val addressSearchRemoteDataSource: AddressSearchRemoteDataSource
): AddressSearchRepository {

    override suspend fun getAddressSearchResult(
        key: String,
        longitude: Double,
        latitude: Double
    ): Result<AddressSearchResponse> {
        return addressSearchRemoteDataSource.getAddressSearchResult(key, longitude, latitude)
    }
}
