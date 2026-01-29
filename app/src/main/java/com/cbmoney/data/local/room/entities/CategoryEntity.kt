package com.cbmoney.data.local.room.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "categories",
    indices = [Index(value = ["userId"])]
)
data class CategoryEntity(
    @PrimaryKey
    val id: String,
    val userId: String? = null,
    val name: String,
    val type: String,
    val icon: String,
    val color: String,
    val order: Int,
    val isDefault: Boolean = true,
    val createdAt: Long
)