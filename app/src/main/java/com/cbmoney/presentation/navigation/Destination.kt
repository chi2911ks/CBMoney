package com.cbmoney.presentation.navigation

import androidx.navigation3.runtime.NavKey
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
    data object LanguageBottomSheet : Destination



    @Serializable
    data object Main : Destination
}