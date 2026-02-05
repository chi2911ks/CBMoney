package com.cbmoney.data.provider.model

import com.google.firebase.auth.FirebaseUser

sealed class AuthResult {
    data class Success(val user: FirebaseUser?): AuthResult()
    data class Failure(val error: AuthError) : AuthResult()
}