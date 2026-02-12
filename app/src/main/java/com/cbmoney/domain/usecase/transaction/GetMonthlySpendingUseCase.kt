package com.cbmoney.domain.usecase.transaction

import com.cbmoney.domain.repository.TransactionRepository

class GetMonthlySpendingUseCase(
    private val repository: TransactionRepository
)
{
    operator fun invoke(
        startDate: Long,
        endDate: Long
    ) = repository.getMonthlySpending(startDate, endDate)
}