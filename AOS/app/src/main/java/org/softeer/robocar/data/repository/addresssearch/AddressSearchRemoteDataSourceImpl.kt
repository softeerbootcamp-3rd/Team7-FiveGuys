package org.softeer.robocar.data.repository.addresssearch

import org.softeer.robocar.data.dto.addresssearch.AddressSearchResponse
import org.softeer.robocar.data.service.addresssearch.AddressSearchService
import javax.inject.Inject

class AddressSearchRemoteDataSourceImpl @Inject constructor(
    private val addressSearchService: AddressSearchService
) : AddressSearchRemoteDataSource {

    override suspend fun getAddressSearchResult(
        key: String,
        query: String
    ): Result<AddressSearchResponse> {
        return runCatching {
            addressSearchService.getAddressSearchResult(key, query)
        }
    }
}
