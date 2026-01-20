package com.cbmoney.presentation.settings.model

import com.cbmoney.R

enum class Languages(val title: String, val subtitle: String, val icon: Int, val code: String) {
    VIETNAMESE("Tiếng Việt", "Vietnamese", R.drawable.flag_vietnam, "vi"),
    ENGLISH("English", "English", R.drawable.flag_united_kingdom, "en"),
}