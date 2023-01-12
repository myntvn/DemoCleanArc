package com.mynt.demo.data.repository.repo

import androidx.paging.*
import com.mynt.demo.database.data.RepoLocalDataSource
import com.mynt.demo.database.model.toDomainModel
import com.mynt.demo.domain.repository.RepoRepository
import com.mynt.demo.model.Repo
import com.mynt.demo.network.data.repo.RepoRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RepoRepositoryImpl @Inject constructor(
    private val repoRemoteDataSource: RepoRemoteDataSource,
    private val repoLocalDataSource: RepoLocalDataSource
) : RepoRepository {

    override fun searchRepos(query: String, perPage: Int): Flow<PagingData<Repo>> {
        return search(query, perPage)
    }

    @OptIn(ExperimentalPagingApi::class)
    private fun search(query: String, perPage: Int): Flow<PagingData<Repo>> {
        return Pager(
            PagingConfig(
                pageSize = perPage
            ),
            remoteMediator = RepoRemoteMediator(
                query = query,
                repoRemoteDataSource = repoRemoteDataSource,
                repoLocalDataSource = repoLocalDataSource
            ),
            pagingSourceFactory = {
                repoLocalDataSource.pagingSource()
            }
        ).flow.map { pagingData ->
            pagingData.map { repo ->
                repo.toDomainModel()
            }
        }

    }
}
