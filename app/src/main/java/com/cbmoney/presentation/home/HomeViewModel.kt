package com.cbmoney.presentation.home

import com.cbmoney.base.BaseMviViewModel

class HomeViewModel(

): BaseMviViewModel<HomeState, HomeEvent, HomeIntent>() {
    init {

    }
    override fun initialState(): HomeState = HomeState()

    override fun processIntent(intent: HomeIntent) {

    }
}