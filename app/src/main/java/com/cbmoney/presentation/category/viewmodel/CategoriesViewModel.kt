package com.cbmoney.presentation.category.viewmodel

import androidx.lifecycle.viewModelScope
import com.cbmoney.base.BaseMviViewModel
import com.cbmoney.domain.usecase.category.DeleteCategoryUseCase
import com.cbmoney.domain.usecase.category.GetAllCategoriesUseCase
import com.cbmoney.presentation.category.contract.CategoriesEvent
import com.cbmoney.presentation.category.contract.CategoriesIntent
import com.cbmoney.presentation.category.contract.CategoriesState
import kotlinx.coroutines.launch

class CategoriesViewModel(
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase,
    private val deleteCategoryUseCase: DeleteCategoryUseCase
): BaseMviViewModel<CategoriesState, CategoriesEvent, CategoriesIntent>() {
    init {
        loadCategories()
    }
    override fun initialState(): CategoriesState = CategoriesState()


    override fun processIntent(intent: CategoriesIntent) {
        when(intent){
            is CategoriesIntent.DeleteCategory -> {
                viewModelScope.launch{
                    deleteCategoryUseCase(intent.categoryId)
                }

            }
        }
    }

    private fun loadCategories() {
        viewModelScope.launch {
            val categories = getAllCategoriesUseCase()
            categories.collect {
                updateState {
                    copy(categories = it)
                }
            }
        }
    }
}

