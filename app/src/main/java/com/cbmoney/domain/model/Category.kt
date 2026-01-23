package com.cbmoney.domain.model

data class Category(
    val id: Int,
    val name: String,
    val type: String,
    val icon: String,
    val color: String,
    val order: Int,
    val isDefault: Boolean,
    val createdAt: String,
)
