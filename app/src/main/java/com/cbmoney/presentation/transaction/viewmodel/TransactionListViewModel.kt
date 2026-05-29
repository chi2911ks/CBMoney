package com.cbmoney.presentation.transaction.viewmodel

import androidx.lifecycle.viewModelScope
import com.cbmoney.base.BaseMviViewModel
import com.cbmoney.domain.model.CategoryType
import com.cbmoney.domain.usecase.category.GetAllCategoriesUseCase
import com.cbmoney.domain.usecase.transaction.GetAllTransactionsUseCase
import com.cbmoney.presentation.transaction.contract.TransactionListEvent
import com.cbmoney.presentation.transaction.contract.TransactionListIntent
import com.cbmoney.presentation.transaction.contract.TransactionListState
import kotlinx.coroutines.launch

class TransactionListViewModel(
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase,
    private val getAllTransactionsUseCase: GetAllTransactionsUseCase
) : BaseMviViewModel<TransactionListState, TransactionListEvent, TransactionListIntent>() {
    init {
        loadData()
    }

    override fun initialState(): TransactionListState = TransactionListState()

    override fun processIntent(intent: TransactionListIntent) {
        when (intent) {
            is TransactionListIntent.SelectCategory -> updateState {
                copy(selectedCategory = intent.category)
            }
            is TransactionListIntent.SelectType -> updateState {
                copy(selectedType = intent.type)
            }
            is TransactionListIntent.SearchQueryChanged -> updateState {
                copy(searchQuery = intent.query)
            }
            TransactionListIntent.LoadTransactions -> loadTransactions()
            is TransactionListIntent.DeleteTransaction -> deleteTransaction(intent.transactionId)
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            getAllCategoriesUseCase().collect { categories ->
                updateState {
                    copy(categories = categories)
                }
            }
        }
        loadTransactions()
    }

    private fun loadTransactions() {
        viewModelScope.launch {
            updateState { copy(isLoading = true) }
            getAllTransactionsUseCase().collect { transactions ->
                val filtered = transactions.filter { transaction ->
                    val matchesCategory = currentState.selectedCategory == null ||
                        transaction.transaction.categoryId == currentState.selectedCategory?.id
                    val matchesType = currentState.selectedType == null ||
                        transaction.transaction.type == currentState.selectedType?.name?.lowercase()
                    val matchesSearch = currentState.searchQuery.isEmpty() ||
                        transaction.categoryName?.contains(currentState.searchQuery, ignoreCase = true) == true ||
                        transaction.transaction.description.contains(currentState.searchQuery, ignoreCase = true)

                    matchesCategory && matchesType && matchesSearch
                }
                updateState {
                    copy(transactions = filtered, isLoading = false)
                }
            }
        }
    }

    private fun deleteTransaction(transactionId: String) {
        viewModelScope.launch {
            try {
                updateState {
                    copy(transactions = transactions.filter { it.transaction.id != transactionId })
                }
                sendEvent(TransactionListEvent.DeleteSuccess)
            } catch (e: Exception) {
                sendEvent(TransactionListEvent.DeleteError(e.message.toString()))
            }
        }
    }
}
