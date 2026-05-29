package com.cbmoney.domain.usecase.transaction

import com.cbmoney.domain.model.Transaction
import com.cbmoney.domain.repository.TransactionRepository

class DeleteTransactionUseCase(
    private val transactionRepository: TransactionRepository
) {
    suspend operator fun invoke(transaction: Transaction): Result<Boolean> {
        return transactionRepository.deleteTransaction(transaction)
    }
}