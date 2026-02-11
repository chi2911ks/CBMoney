package com.cbmoney.domain.model

data class CategorySpending(
    val categoryId: String?,
    val categoryName: String?,
    val categoryIcon: String?,
    val iconColor: String?,
    val type: String,
    val countTransaction: Long,
    val totalSpending: Long
)