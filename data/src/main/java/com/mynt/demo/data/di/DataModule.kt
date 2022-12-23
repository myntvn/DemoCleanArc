package com.mynt.demo.data.di

import com.mynt.demo.data.repository.repo.RepoRepositoryImpl
import com.mynt.demo.domain.repository.RepoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    abstract fun bindsRepoRepository(
        repoRepositoryImpl: RepoRepositoryImpl
    ): RepoRepository

}
