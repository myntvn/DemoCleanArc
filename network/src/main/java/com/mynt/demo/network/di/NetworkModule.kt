package com.mynt.demo.network.di

import com.mynt.demo.network.api.GithubService
import com.mynt.demo.network.api.retrofit
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideGithubService(): GithubService {
        return retrofit.create(GithubService::class.java)
    }

}