package com.cbmoney.domain.model

data class Transaction(
    val id: String = "",
    val userId: String = "",
    val amount: Long,
    val type: String, //("income" | "expense")
    val categoryId: String?,
    val description: String,
    val date: Long,
    val createdAt: Long,
    val updatedAt: Long,
)


