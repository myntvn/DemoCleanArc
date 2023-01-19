package com.mynt.demo.network.model

import com.mynt.demo.model.User

data class UserResponse(
    val id: Long,
    val name: String
)

fun UserResponse.toDomainModel(): User {
    return User(
        id = id,
        name = name
    )
}
