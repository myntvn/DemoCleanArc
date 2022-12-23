package com.mynt.demo.database.di

import android.content.Context
import androidx.room.Room
import com.mynt.demo.database.DemoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDemoDatabase(
        @ApplicationContext context: Context
    ): DemoDatabase = Room.databaseBuilder(
        context,
        DemoDatabase::class.java,
        "demo-database"
    ).build()
}
