package com.mynt.demo.database.data

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class LocalDataSourceModule {

    @Binds
    abstract fun bindsPlantLocalDataSource(
        plantLocalDataSourceImpl: PlantLocalDataSourceImpl
    ): PlantLocalDataSource

    @Binds
    abstract fun bindsRepoLocalDataSource(
        repoLocalDataSourceImpl: RepoLocalDataSourceImpl
    ): RepoLocalDataSource

}
