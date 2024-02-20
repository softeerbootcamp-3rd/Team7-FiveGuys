package org.softeer.robocar.data.repository.auth

import kotlinx.coroutines.flow.Flow
import org.softeer.robocar.data.model.User

interface AuthLocalDataSource {

    suspend fun saveToken(token: String)
    suspend fun getToken(): Flow<String>
    suspend fun saveUserInfo(user: User)
    suspend fun getUserInfo(): Flow<User>

}