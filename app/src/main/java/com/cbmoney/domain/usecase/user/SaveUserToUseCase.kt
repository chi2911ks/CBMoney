package com.cbmoney.domain.usecase.user

import com.cbmoney.data.local.datastore.DataStoreManager
import com.cbmoney.domain.model.User
import com.cbmoney.domain.repository.UserRepository

class SaveUserToUseCase(
    private val userRepository: UserRepository,
    private val dataStoreManager: DataStoreManager
) {
    suspend operator fun invoke(user: User): Result<Boolean> {
        return try {
            val result = userRepository.saveUserFirestore(user)
            if (result.isSuccess) {
                dataStoreManager.saveUserInfo(user)
                Result.success(true)
            } else {
                Result.failure(result.exceptionOrNull() ?: Exception("Unknown error"))
            }
        }catch (e: Exception) {
            Result.failure(e)
        }

    }
}