package com.cbmoney.domain.usecase.transaction

import com.cbmoney.domain.repository.TransactionRepository

class GetCategorySpendingUseCase(
    private val transactionRepository: TransactionRepository
) {
    operator fun invoke(
        startDate: Long,
        endDate: Long
    ) = transactionRepository.getCategorySpending(startDate, endDate)
}