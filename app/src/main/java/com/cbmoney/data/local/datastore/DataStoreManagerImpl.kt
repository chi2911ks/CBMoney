package com.cbmoney.data.local.datastore

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.cbmoney.data.local.datastore.DataStoreKey.USER_INFO
import com.cbmoney.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json

class DataStoreManagerImpl(
    private val dataStore: DataStore<Preferences>
) : DataStoreManager {
    private val json = Json { ignoreUnknownKeys = true }

    override suspend fun saveUserInfo(user: User) {
        try {
            val userInfo = json.encodeToString(user)
            dataStore.edit { preferences ->
                preferences[USER_INFO] = userInfo
            }
        }catch (e: Exception){
            Log.d("DataStoreManager", "error saveUserInfo: $e")
        }
        

    }

    override fun getUserInfo(): Flow<User?> {
        return dataStore.data.map { preferences ->
            val userInfoString = preferences[USER_INFO]
            userInfoString?.let {
                try {
                    json.decodeFromString<User>(it)
                }catch (e: Exception){
                    Log.d("DataStoreManager", "error getUserInfo: $e")
                    null
                }
            }
        }
    }

}