package com.cbmoney.presentation.main

import androidx.lifecycle.ViewModel
import com.cbmoney.presentation.main.model.MainTab
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel: ViewModel(){
    private val _viewState: MutableStateFlow<MainTab> = MutableStateFlow(MainTab.HOME)
    val viewState: StateFlow<MainTab> = _viewState.asStateFlow()
    fun handleNavigateToTab(mainTab: MainTab) {
        _viewState.value = mainTab
    }
}