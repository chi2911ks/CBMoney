package com.cbmoney.data.provider

import android.util.Log
import com.cbmoney.data.provider.model.AuthError
import com.cbmoney.data.provider.model.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import kotlinx.coroutines.tasks.await


class EmailAuth(
    val firebaseAuth: FirebaseAuth
) {
    private val tag = "EmailPasswordAuth"
    suspend fun createAccount(email: String, password: String): AuthResult {
        return try {
            val authUser = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            if (authUser.user != null) {
                AuthResult.Success
            } else {
                AuthResult.Failure(AuthError.Fail)
            }
        } catch (e: Exception) {
            Log.w(tag, "createAccount:failure", e)
            when (e) {
                is FirebaseAuthUserCollisionException -> AuthResult.Failure(AuthError.EmailAlreadyInUse)
                is IllegalArgumentException -> AuthResult.Failure(AuthError.EmptyField)
                else -> AuthResult.Failure(AuthError.Fail)
            }
        }
    }

    suspend fun signIn(email: String, password: String): AuthResult {
        return try {
            val authUser = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            if (authUser.user != null) {
                AuthResult.Success
            } else {
                AuthResult.Failure(AuthError.Fail)
            }
        } catch (e: Exception) {
            Log.w(tag, "signInWithEmail:failure", e)
            when (e) {
                is IllegalArgumentException -> AuthResult.Failure(AuthError.EmptyField)
                is FirebaseAuthInvalidCredentialsException -> AuthResult.Failure(AuthError.InvalidAccount)
                else -> AuthResult.Failure(AuthError.Fail)
            }

        }
    }

}