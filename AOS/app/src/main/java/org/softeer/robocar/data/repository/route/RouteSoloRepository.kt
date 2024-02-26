package org.softeer.robocar.data.repository.route

import org.softeer.robocar.data.model.RouteSolo

interface RouteSoloRepository {
    suspend fun getOptimizedRouteSolo(
        startPoint: String,
        goal: String,
    ): RouteSolo
}