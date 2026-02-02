package com.cbmoney.utils.exts

import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey

fun <T: NavKey> NavBackStack<T>.clearAll(){
    removeAll(this)
}