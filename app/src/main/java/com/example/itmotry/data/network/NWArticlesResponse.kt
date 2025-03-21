package com.example.itmotry.data.network

data class NWArticlesResponse(
    val articles: List<NWArticle>? = null,
)

data class NWArticle(
    val source: NWSource? = null,
    val author: String? = null,
    val title: String? = null,
    val description: String? = null,
    val urlToImage: String? = null,
    val content: String? = null,
)

data class NWSource(
    val id: String? = null,
    val name: String? = null,
)
