package com.cbmoney.presentation.home

import androidx.lifecycle.viewModelScope
import com.cbmoney.base.BaseMviViewModel
import com.cbmoney.data.local.datastore.DataStoreManager
import kotlinx.coroutines.launch

class HomeViewModel(
    private val dataStoreManager: DataStoreManager
): BaseMviViewModel<HomeState, HomeEvent, HomeIntent>() {
    init {
        getUserInfo()
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
}