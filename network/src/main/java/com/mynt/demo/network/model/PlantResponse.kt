package com.mynt.demo.network.model

import com.google.gson.annotations.SerializedName

data class PlantResponse(
    @SerializedName("plantId") val plantId: String,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("imageUrl") val imageUrl: String?,
)
