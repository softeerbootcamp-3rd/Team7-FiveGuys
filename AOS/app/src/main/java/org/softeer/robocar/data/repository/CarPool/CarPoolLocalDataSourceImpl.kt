package org.softeer.robocar.data.repository.CarPool

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import org.softeer.robocar.data.repository.auth.AuthLocalDataSourceImpl
import javax.inject.Inject

class CarPoolLocalDataSourceImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : CarPoolLocalDataSource {

    override suspend fun saveCarPoolId(carPoolId: Long) {
            Log.d("Response", "카풀아이디 저장! $carPoolId")
            context.carPoolDataSource.edit { prefs ->
                prefs[CAR_POOL_ID] = carPoolId
            }

    }

    override suspend fun getCarPoolId(): Long {
        return context.carPoolDataSource.data
            .catch { error ->
                emit(emptyPreferences())
            }
            .map { prefs ->
                prefs[CAR_POOL_ID] ?: -1
            }.first()
    }


    companion object {
        val CAR_POOL_ID = longPreferencesKey("carPoolId")
    }

    private val Context.carPoolDataSource by preferencesDataStore("carPoolDataSource")
}