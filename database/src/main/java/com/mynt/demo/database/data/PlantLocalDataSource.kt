package com.mynt.demo.database.data

import com.mynt.demo.model.Plant
import kotlinx.coroutines.flow.Flow

interface PlantLocalDataSource {

    fun plants(): Flow<List<Plant>>

    suspend fun upsertPlants(plants: List<Plant>)

}
