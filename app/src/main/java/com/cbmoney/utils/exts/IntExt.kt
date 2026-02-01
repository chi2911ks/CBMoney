package com.cbmoney.utils.exts

import androidx.compose.ui.unit.Density

fun Int.toDp(density: Density) = with(density) { toDp() }