package org.softeer.robocar.data.repository.route

import org.softeer.robocar.data.model.Route
import javax.inject.Inject

class RouteRepositoryImpl @Inject constructor(
    private val dataSource: RouteRemoteDataSource,
) : RouteRepository {

    override suspend fun getOptimizedRoute(
        startPoint: String,
        hostGoal: String,
        guestGoal: String
    ): Route {

        return dataSource.getRoute(
            departureAddress = startPoint,
            hostDestAddress = hostGoal,
            guestDestAddress = guestGoal
        )
    }
}
