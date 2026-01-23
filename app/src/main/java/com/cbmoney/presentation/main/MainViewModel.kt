package com.cbmoney.presentation.main

import com.cbmoney.base.BaseMviViewModel
import com.cbmoney.base.MviEvent
import com.cbmoney.base.MviIntent
import com.cbmoney.base.MviState
import com.cbmoney.presentation.main.model.MainTab

data class MainState(
    val currentTab: MainTab = MainTab.HOME
): MviState
sealed class MainEvent: MviEvent {

}
sealed class MainIntent: MviIntent {
    data class NavigateTab(val tab: MainTab): MainIntent()
}
class MainViewModel: BaseMviViewModel<MainState, MainEvent, MainIntent>(){
    override fun initialState(): MainState = MainState()
    override fun processIntent(intent: MainIntent){
        when(intent){
            is MainIntent.NavigateTab -> handleNavigateToTab(intent.tab)
        }
    }
    fun handleNavigateToTab(tab: MainTab) {
        updateState { copy(currentTab = tab) }
    }
}