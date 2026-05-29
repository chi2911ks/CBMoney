package com.cbmoney.presentation.transaction.contract

import com.cbmoney.base.MviContract
import com.cbmoney.domain.model.TransactionDetails

data class TransactionDetailsState(
    val transactionDetails: TransactionDetails? = null,
    val isLoading: Boolean = false
) : MviContract.State

sealed interface TransactionDetailsIntent : MviContract.Intent {
    data object LoadTransaction : TransactionDetailsIntent
    data object DeleteTransaction : TransactionDetailsIntent
    data object EditTransaction : TransactionDetailsIntent
}

sealed interface TransactionDetailsEvent : MviContract.Event {
    data object DeleteSuccess : TransactionDetailsEvent
    data class DeleteError(val message: String) : TransactionDetailsEvent
}