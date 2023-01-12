package com.mynt.demo.data.repository.repo

import android.util.Log
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

    override suspend fun load(loadType: LoadType, state: PagingState<Int, RepoEntity>): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> STARTING_PAGE
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.second ?: return MediatorResult.Success(
                    endOfPaginationReached = true
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
            Log.i("------load", "success")
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } else if (result is Result.Error) {
            Log.i("------load", "exception error")
            return MediatorResult.Error(result.exception)
        }
        Log.i("------load", "exception unknown")
        return MediatorResult.Error(Exception())
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, RepoEntity>): Pair<Int?, Int?>? {
        return state.pages.lastOrNull {
            it.data.isNotEmpty()
        }?.data?.lastOrNull()?.let { repo ->
            repoLocalDataSource.getPagingInfo(repo.id)
        }
    }
}