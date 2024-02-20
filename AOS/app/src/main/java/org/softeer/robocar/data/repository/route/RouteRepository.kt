package org.softeer.robocar.data.repository.route

import org.softeer.robocar.data.model.Route

interface RouteRepository {
    suspend fun getOptimizedRoute(
        startPoint: String,
        hostGoal: String,
        guestGoal: String
    ): Route
}
