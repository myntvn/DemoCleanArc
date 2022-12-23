package com.mynt.demo.database.data

import com.mynt.demo.database.dao.PlantDao
import com.mynt.demo.database.model.PlantEntity
import com.mynt.demo.model.Plant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PlantLocalDataSourceImpl @Inject constructor(
    private val plantDao: PlantDao
) : PlantLocalDataSource {

    override fun plants(): Flow<List<Plant>> {
        return plantDao.getPlantEntitiesStream().map {
            it.map(PlantEntity::toPlantDomainModel)
        }
    }

    override suspend fun upsertPlants(plants: List<Plant>) {
        plantDao.insertOrUpdatePlants(plants.map(Plant::toPlantEntity))
    }
}

private fun Plant.toPlantEntity() = PlantEntity(
    id = id,
    name = name,
    description = description,
    imageUrl = imageUrl
)

private fun PlantEntity.toPlantDomainModel() = Plant(
    id = id,
    name = name,
    description = description,
    imageUrl = imageUrl
)