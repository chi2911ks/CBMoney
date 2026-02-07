package com.cbmoney.presentation.home

import androidx.lifecycle.viewModelScope
import com.cbmoney.base.BaseMviViewModel
import com.cbmoney.data.local.datastore.DataStoreManager
import com.cbmoney.domain.usecase.transaction.GetRecentTransactionsUseCase
import kotlinx.coroutines.launch

class HomeViewModel(
    private val dataStoreManager: DataStoreManager,
    private val getRecentTransactionsUseCase: GetRecentTransactionsUseCase
): BaseMviViewModel<HomeState, HomeEvent, HomeIntent>() {
    init {
        getUserInfo()
        getRecentTransactions()
    }
    override fun initialState(): HomeState = HomeState()

    override fun processIntent(intent: HomeIntent) {

    }
    private fun getUserInfo(){
        viewModelScope.launch {
         dataStoreManager.getUserInfo().collect { user->
             user?.let {
                 updateState { copy(user = user) }
             }
            }
        }

    }
    private fun getRecentTransactions(){
        viewModelScope.launch {
            getRecentTransactionsUseCase().collect {
                updateState {
                    copy(transactions = it)
                }
            }

        }
    }
}