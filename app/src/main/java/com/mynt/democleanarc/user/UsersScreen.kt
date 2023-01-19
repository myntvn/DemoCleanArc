package com.mynt.democleanarc.user

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.mynt.demo.model.User
import com.mynt.democleanarc.repo.*

@Composable
fun UsersScreen(
    viewModel: UserViewModel
) {

    val users: LazyPagingItems<User> = viewModel.users.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            AppBar(
                title = "User",
                onSearch = { }
            )
        }
    ) { padding ->
        when (users.loadState.refresh) {
            is LoadState.Loading -> {
                Loading()
            }
            is LoadState.Error -> {
                Error()
            }
            else -> {
                UserList(users = users, Modifier.padding(padding))
            }
        }
    }

}

@Composable
private fun UserList(
    users: LazyPagingItems<User>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(users, key = {
            it.id
        }) { user ->
            user?.let {
                User(user = user)
            }
        }

        when (users.loadState.append) {
            is LoadState.NotLoading -> Unit
            is LoadState.Loading -> loadingMore()
            is LoadState.Error -> loadingMoreError {
                users.retry()
            }
            else -> Unit
        }
    }
}

@Composable
fun User(user: User) {
    Card(
        elevation = 2.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
        ) {
            Text(
                text = user.id.toString(),
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = user.name
            )
        }
    }
}
