package com.cbmoney.domain.constants

import com.cbmoney.domain.model.CategoryType


object DefaultCategoriesMap {
    val expense = CategoryType.EXPENSE.name.lowercase()
    val income = CategoryType.INCOME.name.lowercase()
    val categories = mapOf(
        CategoryType.EXPENSE to listOf(
            mapOf(
                "name" to "Ăn uống",
                "icon" to "🍔",
                "color" to "#FF6B6B",
                "order" to 1,
                "type" to expense
            ),
            mapOf(
                "name" to "Di chuyển",
                "icon" to "🚗",
                "color" to "#4ECDC4",
                "order" to 2,
                "type" to expense
            ),
            mapOf(
                "name" to "Mua sắm",
                "icon" to "🛍️",
                "color" to "#95E1D3",
                "order" to 3,
                "type" to expense
            ),
            mapOf(
                "name" to "Giải trí",
                "icon" to "🎬",
                "color" to "#F38181",
                "order" to 4,
                "type" to expense
            ),
            mapOf(
                "name" to "Hóa đơn",
                "icon" to "💡",
                "color" to "#FFA07A",
                "order" to 5,
                "type" to expense
            ),
            mapOf(
                "name" to "Y tế",
                "icon" to "💊",
                "color" to "#98D8C8",
                "order" to 6,
                "type" to expense
            ),
            mapOf(
                "name" to "Giáo dục",
                "icon" to "📚",
                "color" to "#6C5CE7",
                "order" to 7,
                "type" to expense
            ),
            mapOf(
                "name" to "Quần áo",
                "icon" to "👕",
                "color" to "#FDCB6E",
                "order" to 8,
                "type" to expense
            ),
            mapOf(
                "name" to "Quần áo",
                "icon" to "👕",
                "color" to "#FDCB6E",
                "order" to 8,
                "type" to expense
            ),
            mapOf(
                "name" to "Khác",
                "icon" to "📦",
                "color" to "#DFE6E9",
                "order" to 9,
                "type" to expense
            )
        ),
        CategoryType.INCOME to listOf(
            mapOf(
                "name" to "Lương",
                "icon" to "💰",
                "color" to "#00B894",
                "order" to 1,
                "type" to income
            ),
            mapOf(
                "name" to "Thưởng",
                "icon" to "🎁",
                "color" to "#00CEC9",
                "order" to 2,
                "type" to income
            ),
            mapOf(
                "name" to "Đầu tư",
                "icon" to "📈",
                "color" to "#0984E3",
                "order" to 3,
                "type" to income
            ),
            mapOf(
                "name" to "Bán đồ",
                "icon" to "🏪",
                "color" to "#FDCB6E",
                "order" to 4,
                "type" to income
            ),
            mapOf(
                "name" to "Khác",
                "icon" to "💵",
                "color" to "#74B9FF",
                "order" to 5,
                "type" to income
            )
        )
    )
}