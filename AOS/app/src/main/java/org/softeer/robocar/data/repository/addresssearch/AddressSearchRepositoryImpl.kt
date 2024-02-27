package org.softeer.robocar.data.repository.addresssearch

import javax.inject.Inject

class AddressSearchRepositoryImpl @Inject constructor(
    private val addressSearchRemoteDataSource: AddressSearchRemoteDataSource
): AddressSearchRepository {

    override suspend fun getAddressSearchResult(
        key: String,
        longitude: Double,
        latitude: Double
    ): Result<String> { // Result<String>으로 반환 타입 변경
        return runCatching {
            val response = addressSearchRemoteDataSource.getAddressSearchResult(key, longitude, latitude).getOrThrow()
            // 도로명 주소가 있으면 도로명 주소 사용, 없으면 일반 주소 사용
            response.documents.firstOrNull()?.let {
                it.road_address?.address_name ?: it.address.address_name
            } ?: throw Exception("주소를 찾을 수 없습니다.")
        }
    }
}
