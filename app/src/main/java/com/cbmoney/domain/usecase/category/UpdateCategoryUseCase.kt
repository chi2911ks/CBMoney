package com.cbmoney.domain.usecase.category

import com.cbmoney.domain.model.Category
import com.cbmoney.domain.repository.CategoryRepository

class UpdateCategoryUseCase(
    private val categoryRepository: CategoryRepository
) {
    suspend operator fun invoke(category: Category): Result<Boolean> {
        return categoryRepository.updateCategory(category)
    }
}