package com.mynt.demo.database.di

import com.mynt.demo.database.DemoDatabase
import com.mynt.demo.database.dao.RepoDao
import com.mynt.demo.database.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {

    @Provides
    fun provideRepoDao(database: DemoDatabase): RepoDao = database.repoDao()

    @Provides
    fun provideUserDao(database: DemoDatabase): UserDao = database.userDao()

}
