package com.mynt.demo.data.repository.repo

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mynt.demo.domain.Result
import com.mynt.demo.model.Repo
import com.mynt.demo.network.data.repo.RepoRemoteDataSource

internal class RepoPagingSource(
    private val repoRemoteDataSource: RepoRemoteDataSource,
    private val query: String,
    private val perPage: Int,
) : PagingSource<Int, Repo>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Repo> {
        return try {
            val nextPageNumber = params.key ?: 1
            val repos = repoRemoteDataSource.getRepos(query, nextPageNumber, perPage)
            if (repos is Result.Success) {
                LoadResult.Page(
                    data = repos.data,
                    prevKey = null,
                    nextKey = nextPageNumber + 1
                )
            } else {
                LoadResult.Error(Exception())
            }
        } catch (e: Exception) {
            Log.e("----load", e.toString())
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Repo>): Int? {
        // Try to find the page key of the closest page to anchorPosition, from
        // either the prevKey or the nextKey, but you need to handle nullability
        // here:
        //  * prevKey == null -> anchorPage is the first page.
        //  * nextKey == null -> anchorPage is the last page.
        //  * both prevKey and nextKey null -> anchorPage is the initial page, so
        //    just return null.
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

}
