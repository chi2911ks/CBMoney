package com.cbmoney.presentation.transaction

import com.cbmoney.base.MviEvent
import com.cbmoney.base.MviIntent
import com.cbmoney.base.MviState
import com.cbmoney.domain.model.Category
import com.cbmoney.domain.model.CategoryType

data class TransactionsState(
    val amount: Long = 0,
    val date: Long = System.currentTimeMillis(),
    val note: String = "",
    val categories: List<Category> = emptyList(),
    val selectedType: CategoryType = CategoryType.EXPENSE,
    val selectedCategory: Category? = null
): MviState
sealed class TransactionsEvent: MviEvent {
    object SaveTransactionSuccess: TransactionsEvent()
    data class SaveTransactionError(val message: String): TransactionsEvent()

}
sealed class TransactionsIntent: MviIntent{
    data class ChangeTab(val type: CategoryType): TransactionsIntent()
    data class SelectCategory(val category: Category): TransactionsIntent()
    data class ChangeAmount(val amount: Long): TransactionsIntent()
    data class ChangeDate(val date: Long): TransactionsIntent()
    data class ChangeNote(val note: String): TransactionsIntent()


    object SaveTransaction: TransactionsIntent()

}