package com.example.itmotry.data.domain

import com.example.itmotry.ui.main.NewsItem

interface NewsRepository {
    suspend fun getAllNews(page: Int): Result<List<NewsItem>>
    suspend fun getNewsByQuery(page: Int, query: String): Result<List<NewsItem>>
}
