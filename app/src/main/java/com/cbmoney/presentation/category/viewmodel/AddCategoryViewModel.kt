package com.cbmoney.presentation.category.viewmodel

import androidx.lifecycle.viewModelScope
import com.cbmoney.base.BaseMviViewModel
import com.cbmoney.domain.model.Category
import com.cbmoney.domain.usecase.category.SaveCategoryUseCase
import com.cbmoney.presentation.category.contract.AddCategoryEvent
import com.cbmoney.presentation.category.contract.AddCategoryIntent
import com.cbmoney.presentation.category.contract.AddCategoryState
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class AddCategoryViewModel(
    private val saveCategoryUseCase: SaveCategoryUseCase,
    private val firebaseAuth: FirebaseAuth
) : BaseMviViewModel<AddCategoryState, AddCategoryEvent, AddCategoryIntent>() {
    override fun initialState(): AddCategoryState = AddCategoryState()


    override fun processIntent(intent: AddCategoryIntent) {
        when (intent) {
            is AddCategoryIntent.SaveCategory -> handleSaveCategory()

            is AddCategoryIntent.OnTypeChanged -> updateState { copy(type = intent.type) }

            is AddCategoryIntent.OnColorSelected -> updateState { copy(color = intent.color) }

            is AddCategoryIntent.OnIconSelected -> updateState { copy(icon = intent.icon) }

            is AddCategoryIntent.OnNameChanged -> updateState { copy(name = intent.name) }

        }
    }

    private fun handleSaveCategory() {
        viewModelScope.launch {
            val name = currentState.name
            val icon = currentState.icon
            if (name.isEmpty()) {
                sendEvent(AddCategoryEvent.ShowError("Name cannot be empty"))
                return@launch
            } else if (icon.isEmpty()) {
                sendEvent(AddCategoryEvent.ShowError("Icon cannot be empty"))
                return@launch
            }
            val userId = firebaseAuth.currentUser?.uid


            val category = Category(
                userId = userId!!,
                name = name,
                icon = icon,
                iconColor = currentState.color,
                type = currentState.type,
                isDefault = false,
            )
            val result = saveCategoryUseCase(category)
            if (result.isSuccess) {
                sendEvent(AddCategoryEvent.NavigateToCategories)
            }
        }
    }
}