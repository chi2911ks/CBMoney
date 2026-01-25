package com.cbmoney.domain.repository

import com.cbmoney.domain.model.User

interface UserRepository {
    suspend fun getUserFirestoreByUid(uid: String): Result<User?>
    suspend fun saveUserFirestore(user: User): Result<Boolean>
    suspend fun initCategoriesDefault(uid: String): Result<Boolean>

}