package com.cbmoney.data.mapper

import com.cbmoney.R
import com.cbmoney.domain.auth.model.AuthError


fun AuthError.toMessage(context: android.content.Context): String {
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

//fun AuthError.toResourceId(): Int {
//    return when (this) {
//        AuthError.EmptyField -> R.string.error_empty_field
//        AuthError.InvalidCredential ->
//            R.string.error_unknown
//
//        AuthError.InvalidEmail ->
//            R.string.error_invalid_email
//
//        AuthError.WrongPassword ->
//            R.string.error_wrong_password
//
//        AuthError.EmailAlreadyInUse ->
//            R.string.error_email_exists
//
//        AuthError.Fail ->
//            R.string.error_unknown
//    }
//}
