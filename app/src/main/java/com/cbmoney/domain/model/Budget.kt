package com.cbmoney.domain.model

data class Budget(
    val id: String,
    val categoryId: String,
    val amount: Long,
    val period: String, //("monthly" | "yearly")
    val month: String, //(format: "YYYY-MM")
    val spent: Int, //(cập nhật khi có transaction mới)
    val createdAt: Long,
)
