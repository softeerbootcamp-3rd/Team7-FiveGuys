package org.softeer.robocar.data.repository.route

import org.softeer.robocar.data.service.RouteService
import org.softeer.robocar.data.mapper.toRoute
import org.softeer.robocar.data.model.Route

class RouteRemoteDataSourceImpl(private val routeService: RouteService) : RouteRemoteDataSource {

    override suspend fun getRoute(
        departureAddress: String,
        hostDestAddress: String,
        guestDestAddress: String
    ): Route {
        val response = routeService.getRoute(departureAddress, hostDestAddress, guestDestAddress)
        return response.toRoute()
    }
}
