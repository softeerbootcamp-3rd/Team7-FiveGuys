package org.softeer.robocar.data.service.operation

import org.softeer.robocar.data.dto.operation.OnboardData
import org.softeer.robocar.data.dto.operation.OnboardResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface OnboardService {
    @GET("/operations/onboard")
    suspend fun getOnboardDetails(
        @Query("inOperationId") inOperationId: Long,
        @Header("Authorization") token: String,
    ): OnboardData
}