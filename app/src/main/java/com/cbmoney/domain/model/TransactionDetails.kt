package com.cbmoney.domain.model

data class TransactionDetails(
    val transaction: Transaction,
    val categoryName: String?,
    val categoryIcon: String?,
    val iconColor: String?,
)
