package com.cbmoney.presentation.app

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class SnackbarManager {
    private val _messages = MutableSharedFlow<UiMessage>(
        extraBufferCapacity = 1
    )
    val messages = _messages.asSharedFlow()

    suspend fun show(message: UiMessage) {
        _messages.emit(message)
    }
}
