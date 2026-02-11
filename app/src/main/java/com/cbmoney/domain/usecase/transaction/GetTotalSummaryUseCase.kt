package com.cbmoney.domain.usecase.transaction

import com.cbmoney.domain.repository.TransactionRepository

class GetTotalSummaryUseCase(
    private val transactionRepository: TransactionRepository
) {
    operator fun invoke(
        startDate: Long,
        endDate: Long
    ) = transactionRepository.getTotalExpenseAndIncome(
        startDate,
        endDate
    )
}