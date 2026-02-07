package com.cbmoney.presentation.category.viewmodel

import androidx.lifecycle.viewModelScope
import com.cbmoney.base.BaseMviViewModel
import com.cbmoney.domain.usecase.category.DeleteCategoryUseCase
import com.cbmoney.domain.usecase.category.SaveCategoryUseCase
import com.cbmoney.presentation.category.contract.EditCategoryEvent
import com.cbmoney.presentation.category.contract.EditCategoryIntent
import com.cbmoney.presentation.category.contract.EditCategoryState
import kotlinx.coroutines.launch

class EditCategoryViewModel(
    private val saveCategoryUseCase: SaveCategoryUseCase,
    private val deleteCategoryUseCase: DeleteCategoryUseCase
):
    BaseMviViewModel<EditCategoryState, EditCategoryEvent, EditCategoryIntent>() {
    override fun initialState(): EditCategoryState = EditCategoryState()


    override fun processIntent(intent: EditCategoryIntent) {
        when(intent){
            is EditCategoryIntent.SaveCategory -> handleSaveCategory()
            is EditCategoryIntent.DeleteCategory -> handleDeleteCategory()

            is EditCategoryIntent.LoadCategoryDefault -> updateState { copy(category = intent.category) }

            is EditCategoryIntent.OnColorSelected -> updateState { copy(category = category.copy(iconColor = intent.color)) }

            is EditCategoryIntent.OnIconSelected ->  updateState { copy(category = category.copy(icon = intent.icon)) }

            is EditCategoryIntent.OnNameChanged -> updateState { copy(category = category.copy(name = intent.name)) }
        }
    }



    private fun handleSaveCategory(){
        viewModelScope.launch {
            val result = saveCategoryUseCase(currentState.category)
            if (result.isSuccess){
                sendEvent(EditCategoryEvent.NavigateToBack)
            }
            else{
                sendEvent(EditCategoryEvent.ShowError("Error"))
            }

        }
    }
    private fun handleDeleteCategory(){
        viewModelScope.launch {
            val result = deleteCategoryUseCase(currentState.category.id)
            if (result.isSuccess){
                sendEvent(EditCategoryEvent.NavigateToBack)
            }
            else{
                sendEvent(EditCategoryEvent.ShowError("Error"))
            }
        }
    }
}