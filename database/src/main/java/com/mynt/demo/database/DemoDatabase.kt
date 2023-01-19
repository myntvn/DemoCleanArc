package com.mynt.demo.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mynt.demo.database.dao.RepoDao
import com.mynt.demo.database.dao.UserDao
import com.mynt.demo.database.model.RepoEntity
import com.mynt.demo.database.model.UserEntity

@Database(
    entities = [
        RepoEntity::class,
        UserEntity::class
    ],
    version = 1
)
abstract class DemoDatabase : RoomDatabase() {
    abstract fun repoDao(): RepoDao

    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: DemoDatabase? = null

        fun getInstance(context: Context): DemoDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context)
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context, DemoDatabase::class.java, "demo-database").build()
    }
}
