package org.softeer.robocar.data.repository.auth

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import org.softeer.robocar.data.model.User
import org.softeer.robocar.di.RoboCarApplication
import javax.inject.Inject

class AuthLocalDataSourceImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : AuthLocalDataSource {

    override suspend fun saveToken(token: String) {
        if (token.isNotEmpty()) {
            context.tokenDataSource.edit { prefs ->
                prefs[ACCESS_TOKEN] = token
                RoboCarApplication.token = token
            }
        }
    }

    override suspend fun getToken(): Flow<String> {
        return context.tokenDataSource.data
            .catch { error ->
                emit(emptyPreferences())
            }
            .map { prefs ->
                prefs.asMap().values.toString()
            }
    }

    override suspend fun saveUserInfo(user: User) {
        context.userDataSource.edit { prefs ->
            prefs[USER_ID] = user.userId
            prefs[USER_NICKNAME] = user.nickname
        }
    }

    override suspend fun getUserInfo(): Flow<User> {
        return context.userDataSource.data
            .catch { error ->
                emit(emptyPreferences())
            }
            .map { prefs ->
                User(
                    prefs.get(USER_ID)!!,
                    prefs.get(USER_NICKNAME)!!
                )
            }
    }

    companion object {
        val ACCESS_TOKEN = stringPreferencesKey("accessToken")
        val USER_ID = longPreferencesKey("userId")
        val USER_NICKNAME = stringPreferencesKey("nickname")
    }

    private val Context.tokenDataSource by preferencesDataStore("tokenDataSource")
    private val Context.userDataSource by preferencesDataStore("userDataSource")
}