package com.mynt.demo.database.data

import com.mynt.demo.database.data.repo.RepoLocalDataSource
import com.mynt.demo.database.data.repo.RepoLocalDataSourceImpl
import com.mynt.demo.database.data.user.UserLocalDataSource
import com.mynt.demo.database.data.user.UserLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class LocalDataSourceModule {

    @Binds
    abstract fun bindsRepoLocalDataSource(
        repoLocalDataSourceImpl: RepoLocalDataSourceImpl
    ): RepoLocalDataSource

    @Binds
    abstract fun bindsUserLocalDataSource(
        userLocalDataSourceImpl: UserLocalDataSourceImpl
    ): UserLocalDataSource

}
