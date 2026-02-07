package com.cbmoney.domain.usecase.category

import com.cbmoney.domain.model.Category
import com.cbmoney.domain.model.CategoryType
import com.cbmoney.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow

class GetCategoriesByTypeUseCase(
    private val categoryRepository: CategoryRepository
) {
    operator fun invoke(type: CategoryType): Flow<List<Category>> =
        categoryRepository.getCategoriesByType(type)
}