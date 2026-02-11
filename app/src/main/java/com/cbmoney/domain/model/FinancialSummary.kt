package com.cbmoney.domain.model

data class FinancialSummary(
    val totalExpense: Long = 0,
    val totalIncome: Long = 0
){
    val revenue  get() = totalIncome - totalExpense
}