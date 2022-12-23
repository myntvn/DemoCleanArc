package com.mynt.demo.domain.repository

import androidx.paging.PagingData
import com.mynt.demo.model.Repo
import kotlinx.coroutines.flow.Flow

interface RepoRepository {

    fun searchRepos(query: String, perPage: Int): Flow<PagingData<Repo>>

}