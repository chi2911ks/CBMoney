package com.cbmoney.data.provider.model

sealed class AuthResult {
    object Success : AuthResult()
    data class Failure(val error: AuthError) : AuthResult()
}