package com.cbmoney.domain.model

import com.google.firebase.Timestamp

data class Transaction(
    val id: Int,
    val amount: Long,
    val type: String, //("income" | "expense")
    val categoryId: String,
    val categoryName: String, //(denormalized để query nhanh)
    val description: String,
    val date: Timestamp,
    val createdAt: Timestamp,
    val updatedAt: Timestamp,
    val attachments: List<String>, //(URLs)
    val tags: List<String>,
    val location: String, //(optional)
    val recurringId: String //(optional, link đến recurring transaction)
)
