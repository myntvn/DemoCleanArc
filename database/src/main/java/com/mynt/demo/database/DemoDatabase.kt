package com.mynt.demo.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mynt.demo.database.dao.PlantDao
import com.mynt.demo.database.model.PlantEntity

@Database(
    entities = [
        PlantEntity::class
    ],
    version = 1
)
abstract class DemoDatabase : RoomDatabase() {
    abstract fun plantDao(): PlantDao
}
