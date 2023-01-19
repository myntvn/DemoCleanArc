package com.mynt.demo.domain.repository

import androidx.paging.PagingData
import com.mynt.demo.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun searchUsers(query: String, perPage: Int): Flow<PagingData<User>>

}
