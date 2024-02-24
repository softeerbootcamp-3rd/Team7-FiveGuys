package org.softeer.robocar.domain.usecase

import org.softeer.robocar.data.model.Route
import org.softeer.robocar.data.repository.route.RouteRepository
import javax.inject.Inject

class GetOptimizedRouteUseCase @Inject constructor(
    private val routeRepository: RouteRepository
) {
    suspend operator fun invoke(
        departureAddress: String,
        hostDestAddress: String,
        guestDestAddress: String,
        hostId: Long,
        guestId: Long
    ): Route {
        return routeRepository.getOptimizedRoute(departureAddress, hostDestAddress, guestDestAddress, hostId, guestId)
    }
}