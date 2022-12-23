package com.mynt.demo.network.model

import com.google.gson.annotations.SerializedName
import com.mynt.demo.model.Repo

data class RepoSearchResponse(
    @SerializedName("total_count") val totalCount: Int = 0,
    @SerializedName("items") val items: List<RepoResponse> = emptyList()
)

data class RepoResponse(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("full_name") val fullName: String,
    @SerializedName("description") val description: String?,
)

fun RepoResponse.toDomainModel(): Repo {
    return Repo(
        id = id,
        name = name,
        fullName = fullName,
        description = description
    )
}
