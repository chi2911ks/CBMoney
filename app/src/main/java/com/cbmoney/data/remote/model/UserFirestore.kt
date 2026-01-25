package com.cbmoney.data.remote.model

import com.google.firebase.Timestamp

data class UserFirestore(
    val uid: String = "",
    val name: String = "",
    val email: String = "",
    val photoUrl: String = "",
    val createdAt: Timestamp? = null,
)
