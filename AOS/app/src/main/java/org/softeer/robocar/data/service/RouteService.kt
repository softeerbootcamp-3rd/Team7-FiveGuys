package org.softeer.robocar.data.service

import org.softeer.robocar.data.dto.route.response.RouteResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RouteService {
    @GET("routes/optimized-route")
    suspend fun getRoute(
        @Query("departureAddress") startPoint: String,
        @Query("hostDestAddress") hostGoal: String,
        @Query("guestDestAddress") guestGoal: String
    ): RouteResponse
}