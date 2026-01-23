package com.cbmoney.presentation.profile

import androidx.lifecycle.viewModelScope
import com.cbmoney.base.BaseMviViewModel
import com.cbmoney.data.provider.GoogleAuth
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch


class ProfileViewModel(
    private val firebaseAuth: FirebaseAuth,
    private val googleAuth: GoogleAuth

) : BaseMviViewModel<ProfileState, ProfileEvent, ProfileIntent>() {
    init {
        loadProfile()
    }
    override fun initialState(): ProfileState = ProfileState()
    override fun processIntent(intent: ProfileIntent) {
        when (intent) {
            is ProfileIntent.LoadProfile -> loadProfile()
            is ProfileIntent.LogOut -> handleLogOut()
        }
    }

    private fun loadProfile() {
        val user = firebaseAuth.currentUser
        updateState {
            copy(
                imageURL = user?.photoUrl?.toString(),
                name = user?.displayName ?: "",
                email = user?.email ?: ""
            )
        }
    }
    private fun handleLogOut(){
        viewModelScope.launch {
            googleAuth.signOut()
            sendEvent(ProfileEvent.LogOut)
        }


    }
}