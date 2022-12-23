package com.mynt.democleanarc

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.mynt.demo.domain.usecase.repo.SearchReposUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val searchReposUseCase: SearchReposUseCase
) : ViewModel() {

    private val query = MutableStateFlow("android")

    @OptIn(ExperimentalCoroutinesApi::class)
    val repos = query.flatMapLatest {
        searchReposUseCase(it).cachedIn(viewModelScope)
    }

    fun search(query: String) {
        if (query.isNotBlank()) {
            this.query.value = query
        }
    }

}
