package com.cbmoney.presentation.transaction.viewmodel

import androidx.lifecycle.viewModelScope
import com.cbmoney.base.BaseMviViewModel
import com.cbmoney.domain.usecase.transaction.DeleteTransactionUseCase
import com.cbmoney.domain.usecase.transaction.GetTransactionByIdUseCase
import com.cbmoney.presentation.transaction.contract.TransactionDetailsEvent
import com.cbmoney.presentation.transaction.contract.TransactionDetailsIntent
import com.cbmoney.presentation.transaction.contract.TransactionDetailsState
import kotlinx.coroutines.launch

class TransactionDetailsViewModel(
    private val getTransactionByIdUseCase: GetTransactionByIdUseCase,
    private val deleteTransactionUseCase: DeleteTransactionUseCase
) : BaseMviViewModel<TransactionDetailsState, TransactionDetailsEvent, TransactionDetailsIntent>() {

    private var currentTransactionId: String? = null

    override fun initialState(): TransactionDetailsState = TransactionDetailsState()

    override fun processIntent(intent: TransactionDetailsIntent) {
        when (intent) {
            TransactionDetailsIntent.LoadTransaction -> loadTransaction()
            TransactionDetailsIntent.DeleteTransaction -> deleteTransaction()
            TransactionDetailsIntent.EditTransaction -> editTransaction()
        }
    }

    fun loadTransactionById(transactionId: String) {
        currentTransactionId = transactionId
        viewModelScope.launch {
            updateState { copy(isLoading = true) }
            val result = getTransactionByIdUseCase(transactionId)
            updateState {
                copy(
                    transactionDetails = result,
                    isLoading = false
                )
            }
        }
    }

    private fun loadTransaction() {
        currentTransactionId?.let { loadTransactionById(it) }
    }

    private fun deleteTransaction() {
        val transaction = currentState.transactionDetails?.transaction ?: return
        viewModelScope.launch {
            deleteTransactionUseCase(transaction).fold(
                onSuccess = {
                    sendEvent(TransactionDetailsEvent.DeleteSuccess)
                },
                onFailure = { e ->
                    sendEvent(TransactionDetailsEvent.DeleteError(e.message.toString()))
                }
            )
        }
    }

    private fun editTransaction() {
        // TODO: navigate to edit screen
    }
}