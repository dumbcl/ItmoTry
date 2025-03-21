package com.example.itmotry.data.domain

import com.example.itmotry.data.network.ApiRepository
import com.example.itmotry.data.network.NWArticle
import com.example.itmotry.ui.main.NewsItem

class NewsRepositoryImpl(
    private val apiRepository: ApiRepository,
) : NewsRepository {
    override suspend fun getAllNews(page: Int): Result<List<NewsItem>> {
        return try {
            val nwNews = apiRepository.getAllNews(page)
            Result.success(
                nwNews.articles?.map { it.toItem() }.orEmpty()
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getNewsByQuery(page: Int, query: String): Result<List<NewsItem>> {
        return try {
            val nwNews = apiRepository.getQueryNews(query, page)
            Result.success(
                nwNews.articles?.map { it.toItem() }.orEmpty()
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun NWArticle.toItem() = NewsItem(
        source = source?.name.orEmpty(),
        title = title.orEmpty(),
        description = description.orEmpty(),
        urlToImage = urlToImage.orEmpty(),
        content = content.orEmpty(),
    )

}
