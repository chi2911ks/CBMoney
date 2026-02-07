package com.cbmoney.domain.usecase.transaction

import com.cbmoney.domain.repository.TransactionRepository

class GetRecentTransactionsUseCase(
    private val transactionRepository: TransactionRepository
) {
    operator fun invoke(limit: Int = 5) = transactionRepository.getRecentTransactions(limit)

}