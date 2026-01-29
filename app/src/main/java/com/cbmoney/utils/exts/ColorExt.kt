package com.cbmoney.utils.exts

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb

fun Color.toHex(): String {
    return String.format(
        "#%08X",
        this.toArgb()
    )
}
