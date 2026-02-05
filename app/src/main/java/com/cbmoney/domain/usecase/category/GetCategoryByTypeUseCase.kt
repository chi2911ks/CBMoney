package com.cbmoney.domain.usecase.category

import com.cbmoney.domain.repository.CategoryRepository

class GetCategoryByTypeUseCase(
    private val categoryRepository: CategoryRepository
) {
    operator fun invoke(type: String) = categoryRepository.getCategoriesByType(type)
}