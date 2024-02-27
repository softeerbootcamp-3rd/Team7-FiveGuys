package org.softeer.robocar.data.repository.route

import org.softeer.robocar.data.mapper.toRouteSolo
import org.softeer.robocar.data.model.RouteSolo
import org.softeer.robocar.data.service.route.RouteSoloService
import javax.inject.Inject

class RouteSoloRemoteDataSourceImpl @Inject constructor(
    private val routeSoloService: RouteSoloService
) : RouteSoloRemoteDataSource {

    override suspend fun getRouteSolo(
        departureAddress: String,
        destAddress: String,
    ): RouteSolo {
        return routeSoloService.getRouteSolo(
            departureAddress,
            destAddress
        ).toRouteSolo()
    }
}