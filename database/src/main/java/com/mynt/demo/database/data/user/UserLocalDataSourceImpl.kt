package com.mynt.demo.database.data.user

import androidx.paging.PagingSource
import com.mynt.demo.database.dao.UserDao
import com.mynt.demo.database.model.UserEntity
import com.mynt.demo.model.User
import javax.inject.Inject

class UserLocalDataSourceImpl @Inject constructor(
    private val userDao: UserDao
) : UserLocalDataSource {

    override fun pagingSource(): PagingSource<Int, UserEntity> {
        return userDao.pagingSource()
    }

    override suspend fun getPagingInfo(userId: Long): Pair<Int?, Int?>? {
        return userDao.getUser(userId)?.let {
            Pair(it.prevPage, it.nextPage)
        }
    }

    override suspend fun insertAll(users: List<User>, pagingInfo: Pair<Int?, Int?>) {
        val userEntities = users.map {user ->
            UserEntity(
                id = user.id,
                name = user.name,
                prevPage = pagingInfo.first,
                nextPage = pagingInfo.second
            )
        }

        userDao.insertAll(userEntities)
    }

}
