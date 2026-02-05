package com.cbmoney.domain.model

data class Budget(
    val id: String = "",
    val userId: String,
    val categoryId: String?,
    val amount: Long = 0,
    val period: String, //("monthly" | "yearly")
    val month: String, //(format: "YYYY-MM")
    val spent: Long = 0, //(cập nhật khi có transaction mới)
    val createdAt: Long = 0,
    val updatedAt: Long = 0,
){
    val remaining: Long
        get() = amount - spent

    val percentage: Float
        get() = if (amount > 0) (spent / amount * 100).toFloat() else 0f

    val isOverBudget: Boolean
        get() = spent > amount

    val isNearLimit: Boolean
        get() = percentage >= 80f
}


data class BudgetCategory(
    val budget: Budget,

    val categoryName: String?,
    val categoryIcon: String?,
    val iconColor: String?,
)