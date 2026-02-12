package com.cbmoney.presentation.transaction.contract

import com.cbmoney.base.MviEvent
import com.cbmoney.base.MviIntent
import com.cbmoney.base.MviState
import com.cbmoney.domain.model.Category
import com.cbmoney.domain.model.CategoryType

data class AddTransactionState(
    val amount: Long = 0,
    val date: Long = System.currentTimeMillis(),
    val note: String = "",
    val categories: List<Category> = emptyList(),
    val selectedType: CategoryType = CategoryType.EXPENSE,
    val selectedCategory: Category? = null
): MviState
sealed class AddTransactionEvent: MviEvent {
    object SaveTransactionSuccess: AddTransactionEvent()
    data class SaveTransactionError(val message: String): AddTransactionEvent()

}
sealed class AddTransactionIntent: MviIntent{
    data class ChangeTab(val type: CategoryType): AddTransactionIntent()
    data class SelectCategory(val category: Category): AddTransactionIntent()
    data class ChangeAmount(val amount: Long): AddTransactionIntent()
    data class ChangeDate(val date: Long): AddTransactionIntent()
    data class ChangeNote(val note: String): AddTransactionIntent()


    object SaveTransaction: AddTransactionIntent()

}