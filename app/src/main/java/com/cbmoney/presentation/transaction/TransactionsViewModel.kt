package com.cbmoney.presentation.transaction

import androidx.lifecycle.viewModelScope
import com.cbmoney.base.BaseMviViewModel
import com.cbmoney.domain.usecase.category.GetCategoriesUseCase
import kotlinx.coroutines.launch

class TransactionsViewModel(
    private val getCategoriesUseCase: GetCategoriesUseCase,
) : BaseMviViewModel<TransactionsState, TransactionsEvent, TransactionsIntent>() {
    init {
        loadData()
    }

    override fun initialState(): TransactionsState = TransactionsState()

    override fun processIntent(intent: TransactionsIntent) {
        when (intent) {
            is TransactionsIntent.ChangeTab -> updateState {
                copy(selectedType = intent.type)
            }

            is TransactionsIntent.SelectCategory -> updateState {
                copy(selectedCategory = intent.category)
            }
            is TransactionsIntent.ChangeAmount -> updateState {
                copy(amount = intent.amount)
            }
            is TransactionsIntent.ChangeDate -> updateState {
                copy(date = intent.date)
            }
            is TransactionsIntent.ChangeNote -> updateState {
                copy(note = intent.note)
            }

        }
    }

    private fun loadData() {
        viewModelScope.launch {
            val result = getCategoriesUseCase()
            result.collect {
                updateState {
                    copy(categories = it)
                }
            }

        }

    }
}