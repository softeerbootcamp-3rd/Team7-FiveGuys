package org.softeer.robocar.data.repository.route

import org.softeer.robocar.data.model.RouteSolo

interface RouteSoloRemoteDataSource {
    suspend fun getRouteSolo(
        departureAddress: String,
        destAddress: String,
    ): RouteSolo
}