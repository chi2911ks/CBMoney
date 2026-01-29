package com.cbmoney.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String,
    val name: String,
    val email: String,
    val photoUrl: String,
    val createdAt: Long,
)

