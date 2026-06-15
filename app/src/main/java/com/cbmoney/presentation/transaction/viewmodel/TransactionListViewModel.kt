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
import kotlinx.coroutines.launch

class TransactionListViewModel(
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase,
    private val getTransactionListUseCase: GetTransactionListUseCase,
) : BaseMviViewModel<TransactionListState, TransactionListEvent, TransactionListIntent>() {
    init {
        loadCategories()
        val currentMonth = java.time.LocalDate.now().monthValue
        getTransactionMonth(currentMonth)
    }

    override fun initialState() = TransactionListState()

    override fun processIntent(intent: TransactionListIntent) {
        when (intent) {
            is TransactionListIntent.LoadTransactions -> getTransactionMonth(intent.month)

        }
    }

    private fun getTransactionMonth(month: Int) {
        val currentYear = System.currentTimeMillis().toFormatDate("yyyy").toInt()
        val (startDate, endDate) = DateUtils.getMonthRange(currentYear, month)
        loadTransactions(startDate, endDate)
    }

    private fun loadTransactions(startDate: Long, endDate: Long) {
        viewModelScope.launch {
            getTransactionListUseCase(startDate, endDate).collect {
                val groupedTransactions = it.groupBy { transactionDetails ->
                    transactionDetails.transaction.date.toStartOfDay()
                }
                Log.d(TAG, "loadTransactions: $groupedTransactions")
                updateState {
                    copy(transactions = groupedTransactions)
                }
            }
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