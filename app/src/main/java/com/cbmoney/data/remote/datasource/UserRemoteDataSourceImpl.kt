package com.cbmoney.data.remote.datasource

import android.util.Log
import com.cbmoney.data.remote.model.UserFirestore
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class UserRemoteDataSourceImpl(
    private val firebaseFirestore: FirebaseFirestore
) : UserRemoteDataSource {

    override suspend fun getUserFirestoreByUid(uid: String): UserFirestore? {
        return try {
            val snapshot = firebaseFirestore
                .collection("users")
                .document(uid)
                .get()
                .await()
            snapshot.toObject(UserFirestore::class.java)
        } catch (e: Exception) {
            Log.w(TAG, "getUserFirestoreByUid: ${e.message}")
            null
        }

    }

    override suspend fun saveUserFirestore(userFirestore: UserFirestore) {

        firebaseFirestore
            .collection("users").document(userFirestore.uid)
            .set(userFirestore)
            .await()

    }



    companion object {
        private const val TAG = "UserRemoteDataSourceImpl"
    }
}