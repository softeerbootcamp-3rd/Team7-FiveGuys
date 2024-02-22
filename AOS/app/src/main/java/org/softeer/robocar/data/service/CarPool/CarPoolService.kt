package org.softeer.robocar.data.service.CarPool

import org.softeer.robocar.data.dto.carpool.request.RequestCarPoolRequest
import org.softeer.robocar.data.dto.carpool.request.registerCarPoolRequest
import org.softeer.robocar.data.dto.carpool.response.CarPoolListResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface CarPoolService {

    @GET("operations/carpools")
    suspend fun getCarPoolList(
        @Query("guestDepartAddress") startLocation: String,
        @Query("guestDestAddress") destinationLocation: String,
        @Query("maleCount") countOfMen: Int,
        @Query("femaleCount") countOfFemale: Int,
    ): CarPoolListResponse

    @POST("operations")
    suspend fun requestCarPool(
        @Body requestCarPoolRequest: RequestCarPoolRequest
    )

    @POST("operations/carpools")
    suspend fun registerCarPool(
        @Body registerCarPoolRequest: registerCarPoolRequest
    )

}