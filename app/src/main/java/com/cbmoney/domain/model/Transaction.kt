package com.cbmoney.domain.model

data class Transaction(
    val id: String,
    val amount: Long,
    val type: String, //("income" | "expense")
    val categoryId: String,
    val categoryName: String, //(denormalized để query nhanh)
    val description: String,
    val date: Long,
    val createdAt: Long,
    val updatedAt: Long,
    val attachments: List<String>, //(URLs)
    val tags: List<String>,
    val location: String, //(optional)
    val recurringId: String //(optional, link đến recurring transaction)
)
