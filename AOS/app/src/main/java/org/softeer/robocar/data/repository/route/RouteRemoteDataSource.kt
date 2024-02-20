package org.softeer.robocar.data.repository.route

import org.softeer.robocar.data.model.Route

interface RouteRemoteDataSource {
    suspend fun getRoute(
        departureAddress: String,
        hostDestAddress: String,
        guestDestAddress: String
    ): Route
}
