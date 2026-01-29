package com.cbmoney.presentation.navigation

import androidx.navigation3.runtime.NavKey
import com.cbmoney.domain.model.Category
import com.cbmoney.domain.model.CategoryType
import kotlinx.serialization.Serializable

@Serializable
sealed interface Destination : NavKey {
    @Serializable
    data object Splash: Destination
    @Serializable
    data object Onboarding : Destination

    @Serializable
    data object Login : Destination

    @Serializable
    data object Register : Destination

    @Serializable
    data object Settings : Destination
    @Serializable
    data object Transaction : Destination
    @Serializable
    data class Categories(val type: CategoryType = CategoryType.EXPENSE) : Destination
    @Serializable
    data class AddCategory(val type: CategoryType = CategoryType.EXPENSE) : Destination
    @Serializable
    data class EditCategory(val category: Category) : Destination

    @Serializable
    data object LanguageBottomSheet : Destination



    @Serializable
    data object Main : Destination
}