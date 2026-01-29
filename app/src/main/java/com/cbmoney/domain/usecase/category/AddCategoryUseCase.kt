package com.cbmoney.domain.usecase.category

import com.cbmoney.domain.model.Category
import com.cbmoney.domain.repository.CategoryRepository

class AddCategoryUseCase (
    private val categoryRepository: CategoryRepository
){
    suspend operator fun invoke(category: Category): Result<Boolean>{
        return categoryRepository.addCategory(category)
    }
}