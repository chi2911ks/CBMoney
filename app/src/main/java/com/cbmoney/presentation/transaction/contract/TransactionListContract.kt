package com.cbmoney.presentation.transaction.contract

import com.cbmoney.base.MviContract
import com.cbmoney.domain.model.Category
import com.cbmoney.domain.model.CategoryType
import com.cbmoney.domain.model.TransactionDetails

data class TransactionListState(
    val transactions: List<TransactionDetails> = emptyList(),
    val categories: List<Category> = emptyList(),
    val selectedCategory: Category? = null,
    val selectedType: CategoryType? = null,
    val searchQuery: String = "",
    val isLoading: Boolean = false
) : MviContract.State

sealed interface TransactionListIntent : MviContract.Intent {
    data class SelectCategory(val category: Category?) : TransactionListIntent
    data class SelectType(val type: CategoryType?) : TransactionListIntent
    data class SearchQueryChanged(val query: String) : TransactionListIntent
    data object LoadTransactions : TransactionListIntent
    data class DeleteTransaction(val transactionId: String) : TransactionListIntent
}

sealed interface TransactionListEvent : MviContract.Event {
    data object DeleteSuccess : TransactionListEvent
    data class DeleteError(val message: String) : TransactionListEvent
}
