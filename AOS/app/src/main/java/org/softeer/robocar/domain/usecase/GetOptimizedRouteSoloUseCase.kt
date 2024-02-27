package org.softeer.robocar.domain.usecase

import org.softeer.robocar.data.model.RouteSolo
import org.softeer.robocar.data.repository.route.RouteSoloRepository
import javax.inject.Inject

class GetOptimizedRouteSoloUseCase @Inject constructor(
    private val routeSoloRepository: RouteSoloRepository
) {
    suspend operator fun invoke(
        departureAddress: String,
        destAddress: String
    ): RouteSolo {
        return routeSoloRepository.getOptimizedRouteSolo(departureAddress, destAddress)
    }
}