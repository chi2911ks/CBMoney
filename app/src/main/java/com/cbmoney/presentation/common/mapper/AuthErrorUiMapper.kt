package com.cbmoney.presentation.common.mapper

import android.content.Context
import com.cbmoney.R
import com.cbmoney.data.provider.model.AuthError

fun AuthError.toMessage(context: Context): String {
    return when (this) {
        AuthError.EmptyField -> context.getString(R.string.error_empty_field)
        AuthError.InvalidCredential ->
            context.getString(R.string.error_unknown)
        AuthError.InvalidAccount ->
            context.getString(R.string.error_invalid_account)
        AuthError.InvalidEmail ->
            context.getString(R.string.error_invalid_email)

        AuthError.WrongPassword ->
            context.getString(R.string.error_wrong_password)

        AuthError.EmailAlreadyInUse ->
            context.getString(R.string.error_email_exists)

        AuthError.Fail ->
            context.getString(R.string.error_unknown)
    }
}