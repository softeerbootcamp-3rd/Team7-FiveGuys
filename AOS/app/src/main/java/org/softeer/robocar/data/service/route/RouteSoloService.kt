package org.softeer.robocar.data.service.route

import org.softeer.robocar.data.dto.route.response.RouteSoloResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RouteSoloService {
    @GET("operations/optimized-route/solo")
    suspend fun getRouteSolo(
        @Query("departureAddress") startPoint: String,
        @Query("destAddress") goal: String
    ): RouteSoloResponse
}