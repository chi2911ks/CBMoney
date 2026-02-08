package com.cbmoney.presentation.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.automirrored.filled.LibraryBooks
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.automirrored.filled.ReceiptLong
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.BakeryDining
import androidx.compose.material.icons.filled.Bed
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Checkroom
import androidx.compose.material.icons.filled.CleaningServices
import androidx.compose.material.icons.filled.Diamond
import androidx.compose.material.icons.filled.DinnerDining
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.DirectionsSubway
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Flight
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalBar
import androidx.compose.material.icons.filled.LocalCafe
import androidx.compose.material.icons.filled.LocalGasStation
import androidx.compose.material.icons.filled.LocalGroceryStore
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.LocalMall
import androidx.compose.material.icons.filled.LocalPizza
import androidx.compose.material.icons.filled.LocalTaxi
import androidx.compose.material.icons.filled.LunchDining
import androidx.compose.material.icons.filled.Medication
import androidx.compose.material.icons.filled.MonitorHeart
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.PedalBike
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.ShoppingBasket
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material.icons.filled.SportsSoccer
import androidx.compose.material.icons.filled.Store
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material.icons.filled.TheaterComedy
import androidx.compose.material.icons.filled.TwoWheeler
import androidx.compose.material.icons.filled.Vaccines
import androidx.compose.material.icons.filled.Watch
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.Weekend
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.ui.graphics.vector.ImageVector

object CategoryIconResolver {
    val CategoryIconMap: Map<String, ImageVector> = hashMapOf(

        // Food & Drink
        "restaurant" to Icons.Filled.Restaurant,
        "fastfood" to Icons.Filled.Fastfood,
        "local_cafe" to Icons.Filled.LocalCafe,
        "local_bar" to Icons.Filled.LocalBar,
        "local_pizza" to Icons.Filled.LocalPizza,
        "lunch_dining" to Icons.Filled.LunchDining,
        "dinner_dining" to Icons.Filled.DinnerDining,
        "bakery_dining" to Icons.Filled.BakeryDining,

        // Transportation
        "directions_car" to Icons.Filled.DirectionsCar,
        "two_wheeler" to Icons.Filled.TwoWheeler,
        "directions_bus" to Icons.Filled.DirectionsBus,
        "local_taxi" to Icons.Filled.LocalTaxi,
        "flight" to Icons.Filled.Flight,
        "directions_subway" to Icons.Filled.DirectionsSubway,
        "pedal_bike" to Icons.Filled.PedalBike,
        "local_gas_station" to Icons.Filled.LocalGasStation,

        // Shopping
        "shopping_bag" to Icons.Filled.ShoppingBag,
        "shopping_cart" to Icons.Filled.ShoppingCart,
        "shopping_basket" to Icons.Default.ShoppingBasket,
        "store" to Icons.Filled.Store,
        "storefront" to Icons.Filled.Storefront,
        "local_mall" to Icons.Filled.LocalMall,
        "local_grocery_store" to Icons.Filled.LocalGroceryStore,

        // Entertainment
        "movie" to Icons.Filled.Movie,
        "sports_esports" to Icons.Filled.SportsEsports,
        "music_note" to Icons.Filled.MusicNote,
        "sports_soccer" to Icons.Filled.SportsSoccer,
        "fitness_center" to Icons.Filled.FitnessCenter,
        "theater_comedy" to Icons.Filled.TheaterComedy,

        // Bills
        "receipt_long" to Icons.AutoMirrored.Filled.ReceiptLong,
        "bolt" to Icons.Filled.Bolt,
        "water_drop" to Icons.Filled.WaterDrop,
        "wifi" to Icons.Filled.Wifi,
        "phone_android" to Icons.Filled.PhoneAndroid,

        // Health
        "local_hospital" to Icons.Filled.LocalHospital,
        "medication" to Icons.Filled.Medication,
        "monitor_heart" to Icons.Filled.MonitorHeart,
        "vaccines" to Icons.Filled.Vaccines,

        // Education
        "school" to Icons.Filled.School,
        "menu_book" to Icons.AutoMirrored.Filled.MenuBook,
        "library_books" to Icons.AutoMirrored.Filled.LibraryBooks,
        "science" to Icons.Filled.Science,

        // Home
        "home" to Icons.Filled.Home,
        "bed" to Icons.Filled.Bed,
        "weekend" to Icons.Filled.Weekend,
        "build" to Icons.Filled.Build,
        "cleaning_services" to Icons.Filled.CleaningServices,

        // Fashion
        "checkroom" to Icons.Filled.Checkroom,
        "watch" to Icons.Filled.Watch,
        "diamond" to Icons.Filled.Diamond,

        // Income
        "attach_money" to Icons.Filled.AttachMoney,
        "payments" to Icons.Filled.Payments,
        "card_giftcard" to Icons.Filled.CardGiftcard,
        "trending_up" to Icons.AutoMirrored.Filled.TrendingUp,
        "account_balance" to Icons.Filled.AccountBalance,

        // Others
        "category" to Icons.Filled.Category,
        "more_horiz" to Icons.Filled.MoreHoriz,
        "help" to Icons.AutoMirrored.Filled.Help
    )

    fun iconOf(key: String): ImageVector {
        return CategoryIconMap[key] ?: Icons.Filled.Category
    }
}