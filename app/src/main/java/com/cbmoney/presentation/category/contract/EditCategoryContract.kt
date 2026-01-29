package com.cbmoney.presentation.category.contract

import com.cbmoney.base.MviEvent
import com.cbmoney.base.MviIntent
import com.cbmoney.base.MviState
import com.cbmoney.domain.model.Category

data class EditCategoryState(
    val category: Category = Category(
        name = "",
        icon = "",
        iconColor = "#BDBDBD",
        isDefault = false
    ),
): MviState

sealed class EditCategoryEvent: MviEvent {
    object NavigateToBack: EditCategoryEvent()
    data class ShowError(val message: String): EditCategoryEvent()

}
sealed class EditCategoryIntent: MviIntent {
    data class OnNameChanged(val name: String) : EditCategoryIntent()
    data class OnColorSelected(val color: String) : EditCategoryIntent()
    data class OnIconSelected(val icon: String) : EditCategoryIntent()
    data class LoadCategoryDefault(val category: Category): EditCategoryIntent()

    object SaveCategory: EditCategoryIntent()
    object DeleteCategory: EditCategoryIntent()




}