package org.softeer.robocar.data.repository.CarPool

interface CarPoolLocalDataSource {
    suspend fun saveCarPoolId(carPoolId: Long)
    suspend fun getCarPoolId(): Long
}