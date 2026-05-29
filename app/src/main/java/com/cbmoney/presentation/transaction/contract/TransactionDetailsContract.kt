package com.cbmoney.presentation.transaction.contract

import com.cbmoney.base.MviContract
import com.cbmoney.domain.model.TransactionDetails

data class TransactionDetailsState(
    val transaction: TransactionDetails? = null,
    val isLoading: Boolean = false,
    val error: String? = null
) : MviContract.State

sealed interface TransactionDetailsIntent : MviContract.Intent {
    data class LoadTransaction(val transactionId: String) : TransactionDetailsIntent
    data object DeleteTransaction : TransactionDetailsIntent
}

sealed interface TransactionDetailsEvent : MviContract.Event {
    data object DeleteSuccess : TransactionDetailsEvent
    data class DeleteError(val message: String) : TransactionDetailsEvent
}
