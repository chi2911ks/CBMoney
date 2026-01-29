package com.cbmoney.domain.usecase.category

import com.cbmoney.domain.repository.CategoryRepository

class DeleteCategoryUseCase(
    private val categoryRepository: CategoryRepository
) {
    suspend operator fun invoke(categoryId: String): Result<Boolean> {
        return categoryRepository.deleteCategory(categoryId)
    }
}