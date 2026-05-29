package com.cbmoney.domain.usecase.transaction

import com.cbmoney.domain.repository.TransactionRepository

class GetAllTransactionsUseCase(
    private val transactionRepository: TransactionRepository
) {
    operator fun invoke() = transactionRepository.getAllTransactionDetails()
}
