package com.cbmoney.utils

import android.content.Context
import android.content.res.Configuration
import java.util.Locale


fun getString(context: Context, resId: Int, locale: String): String{
    val config = Configuration(context.resources.configuration)
    config.setLocale(Locale(locale))
    context.createConfigurationContext(config)
    return context.resources.getString(resId)

}