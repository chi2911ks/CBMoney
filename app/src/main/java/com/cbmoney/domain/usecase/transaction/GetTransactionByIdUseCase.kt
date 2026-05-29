package com.cbmoney.domain.usecase.transaction

import com.cbmoney.domain.repository.TransactionRepository

class GetTransactionByIdUseCase(
    private val transactionRepository: TransactionRepository
) {
    suspend operator fun invoke(transactionId: String) = transactionRepository.getTransactionById(transactionId)
}
