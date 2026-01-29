package com.cbmoney.domain.usecase.category

import com.cbmoney.domain.repository.CategoryRepository

class InitCategoriesDefaultUseCase(
    private val categoryRepository: CategoryRepository,
) {
    suspend operator fun invoke(): Result<Boolean>{
        return categoryRepository.initCategoriesDefault()
    }
}