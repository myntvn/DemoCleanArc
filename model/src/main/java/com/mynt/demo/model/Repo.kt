package com.mynt.demo.model

data class Repo(
    val id: Long,
    val stars: Int,
    val name: String,
    val fullName: String,
    val description: String?,
    val url: String
)
