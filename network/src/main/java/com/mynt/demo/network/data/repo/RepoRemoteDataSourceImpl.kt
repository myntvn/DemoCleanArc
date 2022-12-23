package com.mynt.demo.network.data.repo

import android.util.Log
import com.mynt.demo.domain.Result
import com.mynt.demo.model.Repo
import com.mynt.demo.network.api.GithubService
import com.mynt.demo.network.model.toDomainModel
import javax.inject.Inject

class RepoRemoteDataSourceImpl @Inject constructor(
    private val githubService: GithubService
) : RepoRemoteDataSource {

    private val inQualifier = "in:name,description"

    override suspend fun getRepos(query: String, page: Int, perPage: Int): Result<List<Repo>> {
        return try {
            val response = githubService.searchRepos(query + inQualifier, page, perPage)

            if (response.isSuccessful) {
                Result.Success(response.body()?.items?.map {
                    it.toDomainModel()
                } ?: emptyList())
            } else {
                Result.Error()
            }

        } catch (e: Exception) {
            Log.e("getRepos", e.toString())
            Result.Error()
        }
    }

}
