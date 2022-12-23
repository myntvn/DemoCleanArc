package com.mynt.demo.network.di

import com.mynt.demo.network.data.repo.RepoRemoteDataSource
import com.mynt.demo.network.data.repo.RepoRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteDataSourceModule {

    @Binds
    abstract fun bindsRepoRemoteDataSource(
        repoRemoteDataSourceImpl: RepoRemoteDataSourceImpl
    ): RepoRemoteDataSource

}
