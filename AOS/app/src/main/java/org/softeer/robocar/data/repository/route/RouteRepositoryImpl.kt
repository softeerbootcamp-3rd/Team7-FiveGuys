package org.softeer.robocar.data.repository.route

import kotlinx.coroutines.flow.first
import org.softeer.robocar.data.model.Route
import org.softeer.robocar.data.repository.auth.AuthLocalDataSource
import javax.inject.Inject

class RouteRepositoryImpl @Inject constructor(
    private val dataSource: RouteRemoteDataSource,
    private val authLocalDataSource: AuthLocalDataSource
) : RouteRepository {

    override suspend fun getOptimizedRoute(
        startPoint: String,
        hostGoal: String,
        guestGoal: String,
        hostId: Long,
        guestId: Long
    ): Route {

        return dataSource.getRoute(
            departureAddress = startPoint,
            hostDestAddress = hostGoal,
            guestDestAddress = guestGoal,
            hostId = hostId,
            guestId =guestId,
            authLocalDataSource.getToken().first()
        )
    }
}