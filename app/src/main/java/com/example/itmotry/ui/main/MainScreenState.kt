package com.example.itmotry.ui.main

data class MainScreenState(
    val news: List<NewsItem>,
    val status: LoadingStatus,
    val pagingStatus: PagingStatus,
    val showedNewsIndex: Int?,
) {
    enum class LoadingStatus { LOADING, ERROR, SUCCESS}
    enum class PagingStatus { LOADING, ERROR, SUCCESS}
}

data class NewsItem(
    val source: String,
    val title: String,
    val description: String,
    val urlToImage: String,
    val content: String,
)
