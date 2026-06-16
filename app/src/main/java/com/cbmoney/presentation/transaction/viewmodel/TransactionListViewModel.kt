package com.cbmoney.presentation.transaction.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.cbmoney.base.BaseMviViewModel
import com.cbmoney.domain.usecase.category.GetAllCategoriesUseCase
import com.cbmoney.domain.usecase.transaction.GetTransactionListUseCase
import com.cbmoney.presentation.transaction.contract.TransactionListEvent
import com.cbmoney.presentation.transaction.contract.TransactionListIntent
import com.cbmoney.presentation.transaction.contract.TransactionListState
import com.cbmoney.utils.DateUtils
import com.cbmoney.utils.exts.toFormatDate
import com.cbmoney.utils.exts.toStartOfDay
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class TransactionListViewModel(
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase,
    private val getTransactionListUseCase: GetTransactionListUseCase,
) : BaseMviViewModel<TransactionListState, TransactionListEvent, TransactionListIntent>() {

    private var transactionJob: Job? = null

    init {
        loadCategories()
        val currentMonth = java.time.LocalDate.now().monthValue
        getTransactionMonth(currentMonth)
    }

    override fun initialState() = TransactionListState()

    override fun processIntent(intent: TransactionListIntent) {
        when (intent) {
            is TransactionListIntent.LoadTransactions -> getTransactionMonth(intent.month)
            is TransactionListIntent.FilterTransactions -> {
                updateState {
                    copy(filterType = intent.type, filterCategory = intent.categoryName)
                }
                applyFilters()
            }
        }
    }

    private fun getTransactionMonth(month: Int) {
        val currentYear = System.currentTimeMillis().toFormatDate("yyyy").toInt()
        val (startDate, endDate) = DateUtils.getMonthRange(currentYear, month)
        loadTransactions(startDate, endDate)
    }

    private fun loadTransactions(startDate: Long, endDate: Long) {
        transactionJob?.cancel()
        transactionJob = viewModelScope.launch {
            getTransactionListUseCase(startDate, endDate).collect { transactions ->
                updateState {
                    copy(allTransactions = transactions)
                }
                applyFilters()
            }
        }
    }

    private fun applyFilters() {
        val state = viewState.value
        val filtered = state.allTransactions.filter { details ->
            val matchType = state.filterType == null || details.transaction.type.equals(state.filterType.name, ignoreCase = true)
            val matchCategory = state.filterCategory == null || details.categoryName == state.filterCategory
            matchType && matchCategory
        }
        val groupedTransactions = filtered.groupBy { it.transaction.date.toStartOfDay() }
        updateState {
            copy(transactions = groupedTransactions)
        }
    }

    private fun loadCategories() {
        viewModelScope.launch {
            getAllCategoriesUseCase().collect {
                val mapCategory = it.groupBy { category -> category.type }
                Log.d(TAG, "loadCategories: $mapCategory")
                updateState {
                    copy(mapCategory = mapCategory)
                }
            }
        }
    }

    companion object {
        private const val TAG = "TransactionListViewModel"
    }
}