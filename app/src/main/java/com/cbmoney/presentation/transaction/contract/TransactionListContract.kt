package com.cbmoney.presentation.transaction.contract

import com.cbmoney.base.MviEvent
import com.cbmoney.base.MviIntent
import com.cbmoney.base.MviState
import com.cbmoney.domain.model.Category
import com.cbmoney.domain.model.CategoryType
import com.cbmoney.domain.model.TransactionDetails

data class TransactionListState(
    val isLoading: Boolean = false,
    val mapCategory: Map<CategoryType, List<Category>> = mapOf(),
    val transactions: Map<Long, List<TransactionDetails>> = mapOf(),
    val allTransactions: List<TransactionDetails> = listOf(),
    val filterType: CategoryType? = null,
    val filterCategory: String? = null,
): MviState
sealed class TransactionListEvent: MviEvent {

}
sealed class TransactionListIntent: MviIntent {
    data class LoadTransactions(val month: Int): TransactionListIntent()
    data class FilterTransactions(val type: CategoryType?, val categoryName: String?): TransactionListIntent()
}