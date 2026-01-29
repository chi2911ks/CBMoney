package com.cbmoney.domain.constants

import com.cbmoney.domain.model.Category
import com.cbmoney.domain.model.CategoryType

object DefaultCategories {

    val EXPENSE_CATEGORIES = listOf(
        Category(
            name = "Ăn uống",
            icon = "restaurant",
            iconColor = "#FF8A65", // Cam ấm
            order = 1,
            isDefault = true,
            type = CategoryType.EXPENSE,
        ),
        Category(
            name = "Di chuyển",
            icon = "directions_car",
            iconColor = "#4FC3F7", // Xanh dương
            order = 2,
            isDefault = true,
            type = CategoryType.EXPENSE,
        ),
        Category(
            name = "Mua sắm",
            icon = "shopping_bag",
            iconColor = "#BA68C8", // Tím
            order = 3,
            isDefault = true,
            type = CategoryType.EXPENSE,
        ),
        Category(
            name = "Giải trí",
            icon = "movie",
            iconColor = "#F06292", // Hồng
            order = 4,
            isDefault = true,
            type = CategoryType.EXPENSE,
        ),
        Category(
            name = "Hóa đơn",
            icon = "receipt_long",
            iconColor = "#90A4AE", // Xám xanh
            order = 5,
            isDefault = true,
            type = CategoryType.EXPENSE,
        ),
        Category(
            name = "Khác",
            icon = "more_horiz",
            iconColor = "#BDBDBD", // Xám trung tính
            order = Int.MAX_VALUE,
            isDefault = true,
            type = CategoryType.EXPENSE,
        )
    )

    val INCOME_CATEGORIES = listOf(
        Category(
            name = "Lương",
            icon = "attach_money",
            iconColor = "#66BB6A", // Xanh lá
            order = 1,
            isDefault = true,
            type = CategoryType.INCOME,
        ),
        Category(
            name = "Tiền mặt",
            icon = "payments",
            iconColor = "#FFD54F", // Vàng
            order = 2,
            isDefault = true,
            type = CategoryType.INCOME,
        ),
        Category(
            name = "Đầu tư",
            icon = "trending_up",
            iconColor = "#26A69A", // Xanh ngọc
            order = 3,
            isDefault = true,
            type = CategoryType.INCOME,
        ),
        Category(
            name = "Khác",
            icon = "more_horiz",
            iconColor = "#BDBDBD",
            order = Int.MAX_VALUE,
            isDefault = true,
            type = CategoryType.INCOME,
        )
    )

    val ALL_CATEGORIES = EXPENSE_CATEGORIES + INCOME_CATEGORIES
}
