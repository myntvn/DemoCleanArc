package com.mynt.demo.database.di

import com.mynt.demo.database.DemoDatabase
import com.mynt.demo.database.dao.PlantDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {

    @Provides
    fun providePlantDao(
        database: DemoDatabase
    ): PlantDao = database.plantDao()

}