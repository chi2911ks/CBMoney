package com.cbmoney.presentation.profile

import androidx.lifecycle.viewModelScope
import com.cbmoney.base.BaseMviViewModel
import com.cbmoney.data.local.datastore.DataStoreManager
import com.cbmoney.data.provider.GoogleAuthClient
import kotlinx.coroutines.launch


class ProfileViewModel(
    private val dataStoreManager: DataStoreManager,
    private val googleAuthClient: GoogleAuthClient
) : BaseMviViewModel<ProfileState, ProfileEvent, ProfileIntent>() {
    init {
        getUserInfo()
    }
    override fun initialState(): ProfileState = ProfileState()
    override fun processIntent(intent: ProfileIntent) {
        when (intent) {
            is ProfileIntent.LoadProfile -> getUserInfo()
            is ProfileIntent.LogOut -> handleLogOut()
        }
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
    private fun handleLogOut(){
        viewModelScope.launch {
            updateState { copy(isLoading = true) }
            googleAuthClient.signOut()
            sendEvent(ProfileEvent.LogOut)
            updateState { copy(isLoading = false) }
        }


    }
}