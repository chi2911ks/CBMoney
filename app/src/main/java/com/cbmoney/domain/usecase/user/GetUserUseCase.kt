package com.cbmoney.domain.usecase.user

import com.cbmoney.domain.model.User
import com.cbmoney.domain.repository.UserRepository

class GetUserUseCase(
    private val userRepository: UserRepository
){
    suspend operator fun invoke(uid: String): Result<User?> {
        return userRepository.getUserFirestoreByUid(uid)
    }
}