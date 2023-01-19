package com.mynt.demo.network.data.user

import com.mynt.demo.domain.Result
import com.mynt.demo.model.User
import com.mynt.demo.network.api.GithubService
import com.mynt.demo.network.model.toDomainModel
import javax.inject.Inject

class UserRemoteDataSourceImpl @Inject constructor(
    private val githubService: GithubService
) : UserRemoteDataSource {

    override suspend fun getUsers(query: String, page: Int, perPage: Int): Result<List<User>> {
        return try {
            val response = githubService.getUsers(page, perPage)
            if (response.isSuccessful) {
                Result.Success(response.body()?.map {
                    it.toDomainModel()
                } ?: emptyList())
            } else {
                Result.Error()
            }
        } catch (e: Exception) {
            Result.Error()
        }
    }

}