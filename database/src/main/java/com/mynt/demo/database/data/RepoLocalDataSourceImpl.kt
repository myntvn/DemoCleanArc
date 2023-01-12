package com.mynt.demo.database.data

import androidx.paging.*
import com.mynt.demo.database.dao.RepoDao
import com.mynt.demo.database.model.RepoEntity
import com.mynt.demo.model.Repo
import javax.inject.Inject

class RepoLocalDataSourceImpl @Inject constructor(
    private val repoDao: RepoDao
): RepoLocalDataSource {

    override fun pagingSource(): PagingSource<Int, RepoEntity> {
        return repoDao.pagingSource()
    }

    override suspend fun getPagingInfo(repoId: Long): Pair<Int?, Int?>? {
        return repoDao.getRepo(repoId)?.let {
            Pair(it.prevPage, it.nextPage)
        }
    }

    override suspend fun insertAll(repos: List<Repo>, pagingInfo: Pair<Int?, Int?>) {
        val repoEntities = repos.map { repo ->
            RepoEntity(
                id = repo.id,
                name = repo.name,
                fullName = repo.fullName,
                description = repo.description,
                url = repo.url,
                prevPage = pagingInfo.first,
                nextPage = pagingInfo.second
            )
        }

        repoDao.insertAll(repoEntities)
    }

    override suspend fun clear() {
        repoDao.clearAll()
    }

}
