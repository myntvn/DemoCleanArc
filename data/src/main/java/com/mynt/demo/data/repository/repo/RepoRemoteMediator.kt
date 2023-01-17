package com.mynt.demo.data.repository.repo

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.mynt.demo.database.data.RepoLocalDataSource
import com.mynt.demo.database.model.RepoEntity
import com.mynt.demo.domain.Result
import com.mynt.demo.network.data.repo.RepoRemoteDataSource

private const val STARTING_PAGE = 1

@OptIn(ExperimentalPagingApi::class)
class RepoRemoteMediator(
    private val query: String,
    private val repoRemoteDataSource: RepoRemoteDataSource,
    private val repoLocalDataSource: RepoLocalDataSource
) : RemoteMediator<Int, RepoEntity>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, RepoEntity>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> STARTING_PAGE
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.first
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey

            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.second
                    ?: return MediatorResult.Success(
                        endOfPaginationReached = remoteKeys != null
                    )
                nextKey
            }
        }

        val result = repoRemoteDataSource.getRepos(
            query = query,
            page = page,
            perPage = state.config.pageSize
        )

        if (result is Result.Success) {
            if (loadType == LoadType.REFRESH) {
                repoLocalDataSource.clear()
            }

            val endOfPaginationReached = result.data.isEmpty()

            val prevPage = if (page == STARTING_PAGE) null else page - 1
            val nextPage = if (endOfPaginationReached) null else page + 1

            repoLocalDataSource.insertAll(result.data, Pair(prevPage, nextPage))

            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } else if (result is Result.Error) {
            return MediatorResult.Error(result.exception)
        }
        return MediatorResult.Error(Exception())
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, RepoEntity>): Pair<Int?, Int?>? {
        val page = state.pages.firstOrNull { it.data.isNotEmpty() }
        val lastItem = page?.data?.firstOrNull()
        return lastItem?.let { repo ->
            repoLocalDataSource.getPagingInfo(repo.id)
        }
    }
    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, RepoEntity>): Pair<Int?, Int?>? {
        val page = state.pages.lastOrNull { it.data.isNotEmpty() }
        val lastItem = page?.data?.lastOrNull()
        return lastItem?.let { repo ->
            repoLocalDataSource.getPagingInfo(repo.id)
        }
    }
}
