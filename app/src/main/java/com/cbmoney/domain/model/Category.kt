package com.cbmoney.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Category(
    val id: String = "",
    val userId: String? = null,
    val name: String,
    val type: CategoryType = CategoryType.EXPENSE,
    val icon: String,
    val iconColor: String,
    val order: Int = 0,
    val isDefault: Boolean,
    val createdAt: Long = 0,
)
enum class CategoryType{
    EXPENSE,
    INCOME
}