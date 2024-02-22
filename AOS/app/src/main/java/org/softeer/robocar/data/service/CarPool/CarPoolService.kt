package org.softeer.robocar.data.service.CarPool

import org.softeer.robocar.data.dto.carpool.response.CarPoolListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CarPoolService {

    @GET("operations/carpools")
    suspend fun getCarPoolList(
        @Query("guestDepartAddress") startLocation: String,
        @Query("guestDestAddress") destinationLocation: String,
        @Query("maleCount") countOfMen: Int,
        @Query("femaleCount") countOfFemale: Int,
    ): CarPoolListResponse

}