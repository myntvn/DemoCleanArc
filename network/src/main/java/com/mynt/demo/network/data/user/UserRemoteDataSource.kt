package com.mynt.demo.network.data.user

import com.mynt.demo.domain.Result
import com.mynt.demo.model.User

interface UserRemoteDataSource {

    suspend fun getUsers(query: String, page: Int, perPage: Int): Result<List<User>>

}