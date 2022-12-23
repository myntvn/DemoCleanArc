package com.mynt.demo.data.repository.repo

import androidx.paging.*
import com.mynt.demo.domain.repository.RepoRepository
import com.mynt.demo.model.Repo
import com.mynt.demo.network.data.repo.RepoRemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RepoRepositoryImpl @Inject constructor(
    private val repoRemoteDataSource: RepoRemoteDataSource
) : RepoRepository {

    override fun searchRepos(query: String, perPage: Int): Flow<PagingData<Repo>> =
        Pager(
            PagingConfig(
                pageSize = perPage,
                prefetchDistance = 5
            )
        ) {
            RepoPagingSource(repoRemoteDataSource, query, perPage)
        }.flow

}
