package com.cbmoney.data.provider
import android.app.Activity
import android.util.Log
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import com.cbmoney.R
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.Companion.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await

class GoogleAuthClient(
    private val firebaseAuth: FirebaseAuth
) {
    suspend fun signInGoogle(activity: Activity): Boolean {
        return try {
            val credentialManager = CredentialManager.create(activity)
            val googleIdOption = GetGoogleIdOption.Builder()
                .setServerClientId(activity.getString(R.string.default_web_client_id))
                .setFilterByAuthorizedAccounts(false)
                .build()
            val request = GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build()
            val result = credentialManager.getCredential(context = activity, request = request)
            val credential = result.credential

            if (credential is CustomCredential && credential.type == TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                firebaseAuthWithGoogle(googleIdTokenCredential.idToken)
            } else {
                Log.w(TAG, "Credential is not of type Google ID!")
                false
            }
        } catch (e: Exception) {
            Log.w(TAG, "signInGoogle:failure", e)
            false
        }


    }

    private suspend fun firebaseAuthWithGoogle(idToken: String): Boolean {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        return try {
            val auth = firebaseAuth.signInWithCredential(credential).await()
            Log.d(TAG, "signInWithCredential:success ${auth.user}")
            auth.user != null
        }catch (e: Exception){
            Log.w(TAG, "signInWithCredential:failure", e)
            false
        }
    }

    suspend fun signOut() {
        firebaseAuth.signOut()
        CredentialManager.create(firebaseAuth.app.applicationContext)
            .clearCredentialState(ClearCredentialStateRequest())

    }
    private val TAG = "GoogleAuth"
}