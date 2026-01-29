package com.cbmoney.data.repository

import android.util.Log
import com.cbmoney.data.mapper.toDomain
import com.cbmoney.data.mapper.toUserFirestore
import com.cbmoney.data.remote.datasource.UserRemoteDataSource
import com.cbmoney.domain.model.User
import com.cbmoney.domain.repository.UserRepository

class UserRepositoryImpl(
    private val userRemoteDataSource: UserRemoteDataSource,
) : UserRepository {
    override suspend fun getUserFirestoreByUid(uid: String): Result<User?> {
        return try {
            val userFirestore = userRemoteDataSource.getUserFirestoreByUid(uid)
            if (userFirestore != null) {
                Result.success(userFirestore.toDomain())
            } else {
                Result.success(null)
            }
        } catch (e: Exception) {
            Log.w(TAG, "getUserFirestoreByUid: ${e.message}")
            Result.failure(e)

        }
    }

    override suspend fun saveUserFirestore(user: User): Result<Boolean> {
        return try {
            userRemoteDataSource.saveUserFirestore(user.toUserFirestore())
            Result.success(true)
        } catch (e: Exception) {
            Log.w(TAG, "saveUserFirestore: ${e.message}")
            Result.failure(e)
        }
    }
    companion object {
        private const val TAG = "UserRepositoryImpl"
    }
}