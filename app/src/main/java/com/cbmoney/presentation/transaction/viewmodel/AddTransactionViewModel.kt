package com.cbmoney.presentation.transaction.viewmodel

import androidx.lifecycle.viewModelScope
import com.cbmoney.base.BaseMviViewModel
import com.cbmoney.domain.model.Transaction
import com.cbmoney.domain.usecase.category.GetAllCategoriesUseCase
import com.cbmoney.domain.usecase.transaction.SaveTransactionUseCase
import com.cbmoney.presentation.transaction.contract.AddTransactionEvent
import com.cbmoney.presentation.transaction.contract.AddTransactionIntent
import com.cbmoney.presentation.transaction.contract.AddTransactionState
import kotlinx.coroutines.launch

class AddTransactionViewModel(
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase,
    private val saveTransactionUseCase: SaveTransactionUseCase
) : BaseMviViewModel<AddTransactionState, AddTransactionEvent, AddTransactionIntent>() {
    init {
        loadData()
    }

    override fun initialState(): AddTransactionState = AddTransactionState()

    override fun processIntent(intent: AddTransactionIntent) {
        when (intent) {
            is AddTransactionIntent.ChangeTab -> updateState {
                copy(selectedType = intent.type)
            }

            is AddTransactionIntent.SelectCategory -> updateState {
                copy(selectedCategory = intent.category)
            }
            is AddTransactionIntent.ChangeAmount -> updateState {
                copy(amount = intent.amount)
            }
            is AddTransactionIntent.ChangeDate -> updateState {
                copy(date = intent.date)
            }
            is AddTransactionIntent.ChangeNote -> updateState {
                copy(note = intent.note)
            }

            AddTransactionIntent.SaveTransaction -> saveTransaction()
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
                        updateState {
                            copy(
                                amount = 0,
                                note = ""
                            )
                        }
                        sendEvent(AddTransactionEvent.SaveTransactionSuccess)
                    },
                    onFailure = {
                        sendEvent(AddTransactionEvent.SaveTransactionError(it.message.toString()))
                    }
                )
            }


        }
    }
}