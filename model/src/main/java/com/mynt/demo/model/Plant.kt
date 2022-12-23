package com.mynt.demo.model

data class Plant(
    val id: String,
    val name: String,
    val description: String,
    val imageUrl: String? = null
)
