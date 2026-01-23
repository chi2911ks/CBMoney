package com.cbmoney.domain.auth.model

sealed class AuthResult {
    object Success : AuthResult()
    data class Failure(val error: AuthError) : AuthResult()
}