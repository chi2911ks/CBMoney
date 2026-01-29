package com.cbmoney.data.mapper

import com.cbmoney.data.local.room.entities.CategoryEntity
import com.cbmoney.data.remote.model.CategoryFirestore
import com.cbmoney.domain.model.Category
import com.cbmoney.utils.exts.toCategoryType
// Entity -> Domain
fun CategoryEntity.toDomain(): Category {
    return Category(
        id = id,
        userId = userId,
        name = name,
        type = type.toCategoryType(),
        icon = icon,
        iconColor = color,
        order = order,
        isDefault = isDefault,
        createdAt = createdAt
    )
}
// Domain -> Entity
fun Category.toEntity(): CategoryEntity{
    return CategoryEntity(
        id = id,
        userId = userId,
        name = name,
        type = type.name.lowercase(),
        icon = icon,
        color = iconColor,
        order = order,
        isDefault = isDefault,
        createdAt = createdAt
    )
}
// Firestore -> Entity
fun CategoryFirestore.toEntity(): CategoryEntity {
    return CategoryEntity(
        id = id,
        userId = null,
        name = name,
        type = type,
        icon = icon,
        color = color,
        order = order,
        isDefault = isDefault,
        createdAt = createdAt?.toDate()?.time!!,
    )
}