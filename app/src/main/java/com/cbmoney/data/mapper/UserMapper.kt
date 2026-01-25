package com.cbmoney.data.mapper

import com.cbmoney.data.remote.model.UserFirestore
import com.cbmoney.domain.model.User
import com.google.firebase.Timestamp
import java.util.Date

fun User.toUserFirestore(): UserFirestore {
    return UserFirestore(
        uid = id,
        name = name,
        email = email,
        photoUrl = photoUrl,
        createdAt = Timestamp(Date(createdAt))
    )
}
fun UserFirestore.toDomain(): User {
    return User(
        id = uid,
        name = name,
        email = email,
        photoUrl = photoUrl,
        createdAt = createdAt.toString()
    )
}