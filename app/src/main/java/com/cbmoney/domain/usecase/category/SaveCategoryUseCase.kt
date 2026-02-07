package com.cbmoney.domain.usecase.category

import com.cbmoney.domain.model.Category
import com.cbmoney.domain.repository.CategoryRepository

class SaveCategoryUseCase (
    private val categoryRepository: CategoryRepository
){
    suspend operator fun invoke(category: Category): Result<Boolean>{
        if (category.id.isEmpty()){
            return categoryRepository.addCategory(category)
        }
        return categoryRepository.updateCategory(category)

    }
}