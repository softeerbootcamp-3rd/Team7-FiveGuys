package org.softeer.robocar.data.repository.addresssearch

import org.softeer.robocar.data.dto.addresssearch.AddressInfo
import org.softeer.robocar.data.dto.addresssearch.AddressSearchResponse
import org.softeer.robocar.data.service.addresssearch.AddressSearchService
import javax.inject.Inject

class AddressSearchRemoteDataSourceImpl @Inject constructor(
    private val addressSearchService: AddressSearchService
) : AddressSearchRemoteDataSource {

    override suspend fun getAddressSearchResult(
        key: String,
        longitude: Double,
        latitude: Double
    ): Result<AddressSearchResponse> {
        return runCatching {
            addressSearchService.getAddressSearchResult(key, longitude, latitude)
        }
    }
}
