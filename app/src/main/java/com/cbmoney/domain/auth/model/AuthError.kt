package com.cbmoney.domain.auth.model

sealed class AuthError {
    object InvalidCredential : AuthError()
    object EmptyField : AuthError()
    object EmailAlreadyInUse : AuthError()
    object InvalidEmail : AuthError()
    object Fail : AuthError()
    object WrongPassword : AuthError()
    object InvalidAccount : AuthError()

}
