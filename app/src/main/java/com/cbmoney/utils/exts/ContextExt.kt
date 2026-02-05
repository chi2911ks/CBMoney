package com.cbmoney.utils.exts

import android.app.LocaleManager
import android.content.Context
import android.os.Build
import android.os.LocaleList
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import java.util.Locale

fun Context.getLanguageCode(): String {
    val languageCode = Locale.getDefault().language
    return languageCode
//    return if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//        getSystemService(LocaleManager::class.java).applicationLocales[0]?.toLanguageTag()?.split("-")?.first() ?: "en"
//    } else {
//        AppCompatDelegate.getApplicationLocales()[0]?.toLanguageTag()?.split("-")?.first() ?: "en"
//    }
}
fun Context.handleOnSaveLanguage(languageCode: String) {
    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getSystemService(LocaleManager::class.java).applicationLocales = LocaleList.forLanguageTags(languageCode)
    } else {
        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(languageCode))
    }

//    val intent = packageManager
//        .getLaunchIntentForPackage(packageName)
//        ?.apply {
//            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
//        }
//    startActivity(intent)
//    if (this is Activity) {
//        finish()
//    }
}