package com.mynt.demo.database.data

import androidx.paging.PagingSource
import com.mynt.demo.database.model.RepoEntity
import com.mynt.demo.model.Repo

interface RepoLocalDataSource {
    fun pagingSource(): PagingSource<Int, RepoEntity>

    suspend fun getPagingInfo(repoId: Long): Pair<Int?, Int?>?

    suspend fun insertAll(repos: List<Repo>, pagingInfo: Pair<Int?, Int?>)

    suspend fun clear()
}