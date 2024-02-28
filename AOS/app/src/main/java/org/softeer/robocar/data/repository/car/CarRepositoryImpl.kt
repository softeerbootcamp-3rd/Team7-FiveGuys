package org.softeer.robocar.data.repository.car

import org.softeer.robocar.data.mapper.toCarDetails
import org.softeer.robocar.data.model.CarDetails
import org.softeer.robocar.data.repository.auth.AuthLocalDataSource
import javax.inject.Inject

class CarRepositoryImpl @Inject constructor(
    private val carRemoteDataSource: CarRemoteDataSource,
    private val localAuthDataSource: AuthLocalDataSource,
): CarRepository {
    override suspend fun getCarDetails(
        carId: Long
        ): CarDetails {
        val response = carRemoteDataSource.getCarDetails(carId, localAuthDataSource.getToken()).getOrThrow() // API 호출 결과를 가져옴
        return response.toCarDetails() // DTO에서 Model로 변환
    }

}
