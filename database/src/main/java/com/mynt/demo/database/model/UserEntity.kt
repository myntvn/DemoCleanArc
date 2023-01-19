package com.mynt.demo.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mynt.demo.model.User

@Entity(tableName = "users")
class UserEntity(
    @PrimaryKey val id: Long,
    val name: String,
    val prevPage: Int?,
    val nextPage: Int?
)

fun UserEntity.toDomainModel(): User {
    return User(
        id = id,
        name = name
    )
}
