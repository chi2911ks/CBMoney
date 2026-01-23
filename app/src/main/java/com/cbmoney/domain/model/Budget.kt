package com.cbmoney.domain.model

import com.google.firebase.Timestamp

data class Budget(
    val id: Int,
    val categoryId: String,
    val amount: Long,
    val period: String, //("monthly" | "yearly")
    val month: String, //(format: "YYYY-MM")
    val spent: Int, //(cập nhật khi có transaction mới)
    val createdAt: Timestamp,
)
