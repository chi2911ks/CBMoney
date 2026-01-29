package com.cbmoney.presentation.category.contract

import com.cbmoney.base.MviEvent
import com.cbmoney.base.MviIntent
import com.cbmoney.base.MviState
import com.cbmoney.domain.model.CategoryType
import com.cbmoney.presentation.theme.CBMoneyColors
import com.cbmoney.utils.exts.toHex

data class AddCategoryState(
    val type: CategoryType = CategoryType.EXPENSE,
    val name: String = "",
    val icon: String = "shopping_basket",
    val color: String = CBMoneyColors.Primary.Primary.toHex(),
): MviState

sealed class AddCategoryEvent: MviEvent {
    object NavigateToCategories: AddCategoryEvent()
    data class ShowError(val message: String): AddCategoryEvent()
}
sealed class AddCategoryIntent: MviIntent {
    data class OnNameChanged(val name: String) : AddCategoryIntent()
    data class OnColorSelected(val color: String) : AddCategoryIntent()
    data class OnIconSelected(val icon: String) : AddCategoryIntent()
    data class OnTypeChanged(val type: CategoryType): AddCategoryIntent()

    object SaveCategory: AddCategoryIntent()
}