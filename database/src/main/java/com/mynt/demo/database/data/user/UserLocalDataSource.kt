package com.mynt.demo.database.data.user

import androidx.paging.PagingSource
import com.mynt.demo.database.model.UserEntity
import com.mynt.demo.model.User

interface UserLocalDataSource {
    fun pagingSource(): PagingSource<Int, UserEntity>

    suspend fun getPagingInfo(userId: Long): Pair<Int?, Int?>?

    suspend fun insertAll(users: List<User>, pagingInfo: Pair<Int?, Int?>)
}