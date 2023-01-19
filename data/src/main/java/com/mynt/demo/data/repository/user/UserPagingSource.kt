package com.mynt.demo.data.repository.user

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mynt.demo.domain.Result
import com.mynt.demo.model.User
import com.mynt.demo.network.data.user.UserRemoteDataSource

class UserPagingSource(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val query: String,
    private val perPage: Int
): PagingSource<Int, User>() {

    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        Log.i("-------refreshKey", state.anchorPosition.toString())
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        return try {
            val page = params.key ?: 1
            val response = userRemoteDataSource.getUsers(query, page, perPage)
            if (response is Result.Success) {
                LoadResult.Page(
                    data = response.data,
                    prevKey = null,
                    nextKey = page + 1
                )
            } else {
                LoadResult.Error((response as Result.Error).exception)
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}