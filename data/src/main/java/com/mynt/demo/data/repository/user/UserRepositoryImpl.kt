package com.mynt.demo.data.repository.user

import androidx.paging.*
import com.mynt.demo.database.data.user.UserLocalDataSource
import com.mynt.demo.database.model.toDomainModel
import com.mynt.demo.domain.repository.UserRepository
import com.mynt.demo.model.User
import com.mynt.demo.network.data.user.UserRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val userLocalDataSource: UserLocalDataSource
) : UserRepository {

    //    @OptIn(ExperimentalPagingApi::class)
//    override fun searchUsers(query: String, perPage: Int): Flow<PagingData<User>> {
//        return Pager(
//            config = PagingConfig(
//                pageSize = perPage
//            ),
//            remoteMediator = UserRemoteMediator(
//                query = query,
//                userRemoteDataSource = userRemoteDataSource,
//                userLocalDataSource = userLocalDataSource
//            ),
//            pagingSourceFactory = {
//                userLocalDataSource.pagingSource()
//            }
//        ).flow.map { pagingData ->
//            pagingData.map { user ->
//                user.toDomainModel()
//            }
//        }
//    }
    override fun searchUsers(query: String, perPage: Int): Flow<PagingData<User>> {
        return Pager(
            PagingConfig(
                pageSize = perPage,
                prefetchDistance = 5
            ),
            pagingSourceFactory = {
                UserPagingSource( userRemoteDataSource, query, perPage)
            }
        ).flow
    }

}
