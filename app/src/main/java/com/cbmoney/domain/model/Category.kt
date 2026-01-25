package com.cbmoney.domain.model

import com.google.firebase.Timestamp

data class Category(
    val id: String,
    val name: String,
    val type: String,
    val icon: String,
    val color: String,
    val order: Int,
    val isDefault: Boolean,
    val createdAt: Timestamp,
)
