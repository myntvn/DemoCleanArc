package com.mynt.demo.domain.usecase.user

import androidx.paging.PagingData
import com.mynt.demo.domain.repository.UserRepository
import com.mynt.demo.model.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchUsersUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    companion object {
        private const val PER_PAGE = 20
    }

    operator fun invoke(query: String): Flow<PagingData<User>> {
        return userRepository.searchUsers(query, PER_PAGE)
    }

}
