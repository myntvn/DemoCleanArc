package com.mynt.demo.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mynt.demo.model.Repo

@Entity(tableName = "repos")
class RepoEntity(
    @PrimaryKey val id: Long,
    val name: String,
    @ColumnInfo(name = "full_name") val fullName: String,
    val description: String?,
    val url: String,
    val prevPage: Int?,
    val nextPage: Int?
)

fun RepoEntity.toDomainModel(): Repo {
    return Repo(
        id = id,
        name = name,
        fullName = fullName,
        description = description,
        url = url
    )
}
