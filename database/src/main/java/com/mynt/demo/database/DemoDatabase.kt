package com.mynt.demo.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mynt.demo.database.dao.RepoDao
import com.mynt.demo.database.model.RepoEntity

@Database(
    entities = [
        RepoEntity::class
    ],
    version = 1
)
abstract class DemoDatabase : RoomDatabase() {
    abstract fun repoDao(): RepoDao
}
