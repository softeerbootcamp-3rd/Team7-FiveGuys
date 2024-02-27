package org.softeer.robocar.data.service.CarPool

import org.softeer.robocar.data.dto.carpool.request.RejectCarPoolRequest
import org.softeer.robocar.data.dto.carpool.request.RequestCarPoolRequest
import org.softeer.robocar.data.dto.carpool.request.registerCarPoolRequest
import org.softeer.robocar.data.dto.carpool.response.AcceptCarPoolResponse
import org.softeer.robocar.data.dto.carpool.response.CarPoolListResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface CarPoolService {

    @GET("operations/carpools")
    suspend fun getCarPoolList(
        @Query("guestDepartAddress") startLocation: String,
        @Query("guestDestAddress") destinationLocation: String,
        @Query("maleCount") countOfMen: Int,
        @Query("femaleCount") countOfFemale: Int,
        @Header("Authorization") token: String,
    ): CarPoolListResponse

    @POST("operations/carpool/request")
    suspend fun requestCarPool(
        @Body request: RequestCarPoolRequest,
        @Header("Authorization") token: String,
    )

    @POST("operations/carpools")
    suspend fun registerCarPool(
        @Body registerCarPoolRequest: registerCarPoolRequest,
        @Header("Authorization") token: String,
    )

    @POST("operations/carpool/reject")
    suspend fun rejectCarPoolRequest(
        @Query("guestId") guestId: Long,
        @Header("Authorization") token: String,
    )

    @DELETE("operations/carpools")
    suspend fun acceptCarPoolRequest(
        @Query("guestId") guestId: Long,
        @Query("guestDestAddress") guestDestination: String,
        @Header("Authorization") token: String,
    ): AcceptCarPoolResponse

    @GET("operations/onboard")
    suspend fun checkOperationStatus(
        @Query("inOperationId") carPoolId: Long,
        @Header("Authorization") token: String,
    )

}