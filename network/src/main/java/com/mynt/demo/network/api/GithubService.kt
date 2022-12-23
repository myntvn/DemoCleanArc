package com.mynt.demo.network.api

import com.mynt.demo.network.model.RepoSearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubService {

    @GET("search/repositories?sort=stars")
    suspend fun searchRepos(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") itemsPerPage: Int
    ): Response<RepoSearchResponse>

    companion object {
        const val BASE_URL = "https://api.github.com/"
    }

}