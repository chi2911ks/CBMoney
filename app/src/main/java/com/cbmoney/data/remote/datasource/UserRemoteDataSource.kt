package com.cbmoney.data.remote.datasource

import com.cbmoney.data.remote.model.UserFirestore

interface UserRemoteDataSource {
    suspend fun getUserFirestoreByUid(uid: String): UserFirestore?
    suspend fun saveUserFirestore(userFirestore: UserFirestore)

}