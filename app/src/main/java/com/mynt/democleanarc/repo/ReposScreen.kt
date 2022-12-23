package com.mynt.democleanarc.repo

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.mynt.demo.model.Repo
import com.mynt.democleanarc.MainViewModel

@Composable
fun ReposScreen(
    viewModel: MainViewModel
) {
    val repos: LazyPagingItems<Repo> = viewModel.repos.collectAsLazyPagingItems()

    var query by remember {
        mutableStateOf("android")
    }

    Scaffold(
        topBar = {
            TopAppBar {
                Row {
                    TextField(
                        value = query,
                        onValueChange = {
                            query = it
                        },
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = TextStyle(color = Color.White),
                        singleLine = true,
                        trailingIcon = {
                            IconButton(
                                onClick = { viewModel.search(query) }
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Search,
                                    contentDescription = "search",
                                    tint = Color.White
                                )
                            }
                        }
                    )
                }
            }
        }
    ) { padding ->
        when (repos.loadState.refresh) {
            is LoadState.Loading -> {
                Loading()
            }
            is LoadState.Error -> {
                Error()
            }
            else -> {
                RepoList(repos = repos, Modifier.padding(padding))
            }
        }
    }
}

@Composable
private fun RepoList(
    repos: LazyPagingItems<Repo>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(repos) { repo ->
            GithubRepo(repo = repo)
        }

        when (repos.loadState.append) {
            is LoadState.NotLoading -> Unit
            is LoadState.Loading -> loadingMore()
            is LoadState.Error -> loadingMoreError()
            else -> Unit
        }
    }
}

@Composable
fun GithubRepo(repo: Repo?) {
    Card(
        elevation = 2.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
        ) {
            Text(
                text = repo?.fullName ?: "empty name",
                fontWeight = FontWeight.Bold
            )

            Text(
                text = repo?.description ?: "empty description"
            )
        }
    }
}

@Composable
private fun Loading() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Loading...",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}

private fun LazyListScope.loadingMore() {
    item {
        Text(
            text = "Loading more...",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun Error() {
    Text(
        text = "Error",
        modifier = Modifier.fillMaxSize(),
        textAlign = TextAlign.Center
    )
}

private fun LazyListScope.loadingMoreError() {
    item {
        Text(
            text = "Load more error",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}
