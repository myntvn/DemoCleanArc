package com.mynt.demo.domain.usecase.repo

import androidx.paging.PagingData
import com.mynt.demo.domain.repository.RepoRepository
import com.mynt.demo.model.Repo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchReposUseCase @Inject constructor(
    private val repoRepository: RepoRepository
) {

    private val perPage = 20

    operator fun invoke(query: String): Flow<PagingData<Repo>> {
        return repoRepository.searchRepos(query, perPage)
    }

    companion object {
        const val DEFAULT_QUERY = "android"
    }

}
