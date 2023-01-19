package com.mynt.demo.data.repository.user

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.mynt.demo.database.data.user.UserLocalDataSource
import com.mynt.demo.database.model.UserEntity
import com.mynt.demo.domain.Result
import com.mynt.demo.network.data.user.UserRemoteDataSource

private const val STARTING_PAGE = 1

@OptIn(ExperimentalPagingApi::class)
class UserRemoteMediator(
    private val query: String,
    private val userRemoteDataSource: UserRemoteDataSource,
    private val userLocalDataSource: UserLocalDataSource
) : RemoteMediator<Int, UserEntity>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, UserEntity>
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

        val result = userRemoteDataSource.getUsers(
            query = query,
            page = page,
            perPage = state.config.pageSize
        )

        if (result is Result.Success) {
//            if (loadType == LoadType.REFRESH) {
//                repoLocalDataSource.clear()
//            }

            val endOfPaginationReached = result.data.isEmpty()

            val prevPage = if (page == STARTING_PAGE) null else page - 1
            val nextPage = if (endOfPaginationReached) null else page + 1

            userLocalDataSource.insertAll(result.data, Pair(prevPage, nextPage))

            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } else if (result is Result.Error) {
            return MediatorResult.Error(result.exception)
        }
        return MediatorResult.Error(Exception())
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, UserEntity>): Pair<Int?, Int?>? {
        val page = state.pages.firstOrNull { it.data.isNotEmpty() }
        val lastItem = page?.data?.firstOrNull()
        return lastItem?.let { repo ->
            userLocalDataSource.getPagingInfo(repo.id)
        }
    }
    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, UserEntity>): Pair<Int?, Int?>? {
        val page = state.pages.lastOrNull { it.data.isNotEmpty() }
        val lastItem = page?.data?.lastOrNull()
        return lastItem?.let { repo ->
            userLocalDataSource.getPagingInfo(repo.id)
        }
    }
}
