package com.cbmoney.presentation.transaction.viewmodel

import androidx.lifecycle.viewModelScope
import com.cbmoney.base.BaseMviViewModel
import com.cbmoney.domain.usecase.transaction.GetTransactionByIdUseCase
import com.cbmoney.presentation.transaction.contract.TransactionDetailsEvent
import com.cbmoney.presentation.transaction.contract.TransactionDetailsIntent
import com.cbmoney.presentation.transaction.contract.TransactionDetailsState
import kotlinx.coroutines.launch

class TransactionDetailsViewModel(
    private val getTransactionByIdUseCase: GetTransactionByIdUseCase
) : BaseMviViewModel<TransactionDetailsState, TransactionDetailsEvent, TransactionDetailsIntent>() {

    override fun initialState(): TransactionDetailsState = TransactionDetailsState()

    override fun processIntent(intent: TransactionDetailsIntent) {
        when (intent) {
            is TransactionDetailsIntent.LoadTransaction -> loadTransaction(intent.transactionId)
            TransactionDetailsIntent.DeleteTransaction -> deleteTransaction()
        }
    }

    private fun loadTransaction(transactionId: String) {
        viewModelScope.launch {
            updateState { copy(isLoading = true) }
            try {
                val transaction = getTransactionByIdUseCase(transactionId)
                updateState {
                    copy(transaction = transaction, isLoading = false, error = null)
                }
            } catch (e: Exception) {
                updateState {
                    copy(isLoading = false, error = e.message)
                }
            }
        }
    }

    private fun deleteTransaction() {
        viewModelScope.launch {
            try {
                currentState.transaction?.let {
                    // TODO: implement delete logic
                    sendEvent(TransactionDetailsEvent.DeleteSuccess)
                }
            } catch (e: Exception) {
                sendEvent(TransactionDetailsEvent.DeleteError(e.message.toString()))
            }
        }
    }
}
