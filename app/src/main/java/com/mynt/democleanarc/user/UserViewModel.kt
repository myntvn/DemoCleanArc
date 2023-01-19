package com.mynt.democleanarc.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.mynt.demo.domain.usecase.user.SearchUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val searchUsersUseCase: SearchUsersUseCase
): ViewModel() {

    val users = searchUsersUseCase("").cachedIn(viewModelScope)

}