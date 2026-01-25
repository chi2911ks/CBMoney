package com.cbmoney.data.remote.model

import com.google.firebase.Timestamp

data class CategoryFirestore(
    val categoryId: String = "",
    val name: String = "",
    val type: String = "",
    val icon: String = "",
    val color: String = "",
    val order: Int = 0,
    val isDefault: Boolean = true,
    val createdAt: Timestamp? = null,
)