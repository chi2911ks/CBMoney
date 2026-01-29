package com.cbmoney.presentation.category.contract

import com.cbmoney.base.MviEvent
import com.cbmoney.base.MviIntent
import com.cbmoney.base.MviState
import com.cbmoney.domain.model.Category

data class CategoriesState(
    val categories: List<Category> = emptyList()
): MviState

sealed class CategoriesEvent: MviEvent {   }
sealed class CategoriesIntent: MviIntent {
    data class DeleteCategory(val categoryId: String): CategoriesIntent()
}