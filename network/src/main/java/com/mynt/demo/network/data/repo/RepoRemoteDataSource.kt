package com.mynt.demo.network.data.repo

import com.mynt.demo.domain.Result
import com.mynt.demo.model.Repo

interface RepoRemoteDataSource {

    suspend fun getRepos(query: String, page: Int, perPage: Int): Result<List<Repo>>

}
