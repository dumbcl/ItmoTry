package com.example.itmotry.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.itmotry.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    uiState: MainScreenState,
    retry: () -> Unit,
    load: () -> Unit,
    loadMore: () -> Unit,
    onNewsClicked: (Int) -> Unit,
    closeDialog: () -> Unit,
    find: (String) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        stringResource(R.string.news)
                    )
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues),
            contentAlignment = Alignment.Center,
        ) {
            when (uiState.status) {
                MainScreenState.LoadingStatus.LOADING -> LoadingState()
                MainScreenState.LoadingStatus.ERROR -> ErrorState(retry = retry)
                MainScreenState.LoadingStatus.SUCCESS -> LoadedState(
                    news = uiState.news,
                    loadMore = loadMore,
                    find = find,
                    onNewsClicked = onNewsClicked,
                    pagingStatus = uiState.pagingStatus
                )
            }
            val index = uiState.showedNewsIndex
            if (index != null) {
                NewsDialog(
                    newsItem = uiState.news[index],
                    close = closeDialog,
                )
            }
        }
    }
}

@Composable
private fun LoadingState() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surfaceContainerLow)
            .padding(top = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(R.string.loading),
            style = MaterialTheme.typography.headlineMedium,
        )
        Spacer(modifier = Modifier.height(12.dp))
        CircularProgressIndicator()
    }
}

@Composable
private fun ErrorState(
    retry: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surfaceContainerLow)
            .padding(top = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(R.string.error),
            style = MaterialTheme.typography.headlineMedium,
        )
        Spacer(modifier = Modifier.height(12.dp))
        Button(
            onClick = retry,
        ) {
            Text(stringResource(R.string.try_again))
        }
    }
}

@Composable
private fun LoadedState(
    news: List<NewsItem>,
    loadMore: () -> Unit,
    find: (String) -> Unit,
    onNewsClicked: (Int) -> Unit,
    pagingStatus: MainScreenState.PagingStatus,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surfaceContainerLow),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        itemsIndexed(news) { index, item ->
            NewsSnippet(
                newsItem = item,
                modifier = Modifier.clickable(onClick = { onNewsClicked(index) })
            )
        }
       when (pagingStatus) {
           MainScreenState.PagingStatus.LOADING -> item {
               LoadingState()
           }
           MainScreenState.PagingStatus.ERROR -> item {
               ErrorState(
                   retry = loadMore,
               )
           }
           MainScreenState.PagingStatus.SUCCESS -> item {
               Button(
                   onClick = loadMore
               ) {
                   Text(stringResource(R.string.load_more))
               }
           }
       }
    }
}
