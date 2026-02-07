package com.cbmoney.presentation.transaction

import androidx.lifecycle.viewModelScope
import com.cbmoney.base.BaseMviViewModel
import com.cbmoney.domain.model.Transaction
import com.cbmoney.domain.usecase.category.GetAllCategoriesUseCase
import com.cbmoney.domain.usecase.transaction.SaveTransactionUseCase
import kotlinx.coroutines.launch

class TransactionsViewModel(
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase,
    private val saveTransactionUseCase: SaveTransactionUseCase
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

            TransactionsIntent.SaveTransaction -> saveTransaction()
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            val result = getAllCategoriesUseCase()
            result.collect {
                updateState {
                    copy(categories = it)
                }
            }

        }

    }
    private fun saveTransaction(){
        viewModelScope.launch {
            currentState.selectedCategory?.let {
                val transaction = Transaction(
                    amount = currentState.amount,
                    type = currentState.selectedType.name.lowercase(),
                    categoryId = it.id,
                    description = currentState.note,
                    date = currentState.date,
                    createdAt = System.currentTimeMillis(),
                    updatedAt = System.currentTimeMillis(),
                )
                saveTransactionUseCase(transaction).fold(
                    onSuccess = {
                        sendEvent(TransactionsEvent.SaveTransactionSuccess)
                    },
                    onFailure = {
                        sendEvent(TransactionsEvent.SaveTransactionError(it.message.toString()))
                    }
                )
            }


        }
    }
}