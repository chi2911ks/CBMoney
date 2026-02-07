package com.cbmoney.presentation.app

import androidx.annotation.StringRes

sealed interface UiMessage {
    data class Text(val text: String) : UiMessage
    data class Res(
        @param:StringRes val resId: Int,
        val args: List<Any> = emptyList()
    ) : UiMessage
}
