package org.softeer.robocar.data.repository.route

import org.softeer.robocar.data.model.RouteSolo
import javax.inject.Inject

class RouteSoloRepositoryImpl @Inject constructor(
    private val dataSource: RouteSoloRemoteDataSource,
) : RouteSoloRepository {

    override suspend fun getOptimizedRouteSolo(
        startPoint: String,
        goal: String
    ): RouteSolo {

        return dataSource.getRouteSolo(
            departureAddress = startPoint,
            destAddress = goal
        )
    }
}