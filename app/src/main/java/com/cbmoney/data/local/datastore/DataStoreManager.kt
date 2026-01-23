package com.cbmoney.data.local.datastore

import com.cbmoney.domain.model.User
import kotlinx.coroutines.flow.Flow

interface DataStoreManager{
    suspend fun saveUserInfo(user: User)
    fun getUserInfo(): Flow<User?>
}