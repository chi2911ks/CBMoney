package com.cbmoney.presentation.home

import androidx.lifecycle.viewModelScope
import com.cbmoney.base.BaseMviViewModel
import com.cbmoney.data.local.datastore.DataStoreManager
import com.cbmoney.domain.usecase.transaction.GetMonthlySpendingUseCase
import com.cbmoney.domain.usecase.transaction.GetRecentTransactionsUseCase
import com.cbmoney.utils.DateUtils
import com.cbmoney.utils.exts.toFormatDate
import kotlinx.coroutines.launch

class HomeViewModel(
    private val dataStoreManager: DataStoreManager,
    private val getRecentTransactionsUseCase: GetRecentTransactionsUseCase,
    private val getMonthlySpendingUseCase: GetMonthlySpendingUseCase
): BaseMviViewModel<HomeState, HomeEvent, HomeIntent>() {
    init {
        getUserInfo()
        getRecentTransactions()
        getMonthlySpending()
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
    private fun getMonthlySpending(){
        val (startDate, endDate) = DateUtils.getYearRange(System.currentTimeMillis().toFormatDate("yyyy").toInt())
        viewModelScope.launch {
            getMonthlySpendingUseCase(startDate, endDate).collect {
                updateState {
                    copy(monthlySpending = it)
                }
            }

        }
    }
}