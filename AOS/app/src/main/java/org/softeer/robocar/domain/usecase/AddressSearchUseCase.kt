package org.softeer.robocar.domain.usecase

import org.softeer.robocar.data.repository.addresssearch.AddressSearchRepository
import javax.inject.Inject

class AddressSearchUseCase @Inject constructor(
    private val addressSearchRepository: AddressSearchRepository
) {
    suspend operator fun invoke(apiKey: String, latitude: Double, longitude: Double): Result<String> {
        return addressSearchRepository.getAddressSearchResult(apiKey, latitude, longitude)
    }
}
