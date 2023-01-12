package com.mynt.demo.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mynt.demo.database.model.RepoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RepoDao {

    @Query(value = "SELECT * FROM repos")
    fun getRepos(): Flow<List<RepoEntity>>

    @Query("SELECT * FROM repos WHERE id = :id")
    suspend fun getRepo(id: Long): RepoEntity?

    @Query("SELECT * FROM repos")
    fun pagingSource(): PagingSource<Int, RepoEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(repos: List<RepoEntity>)

    @Query("DELETE FROM repos")
    suspend fun clearAll()

}
