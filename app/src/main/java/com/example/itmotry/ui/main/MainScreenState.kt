package com.example.itmotry.ui.main

data class MainScreenState(
    val news: List<NewsItem>,
    val status: LoadingStatus,
) {
    enum class LoadingStatus { LOADING, ERROR, SUCCESS }
}

data class NewsItem(
    val source: String,
    val title: String,
    val description: String,
    val urlToImage: String,
    val content: String,
)
