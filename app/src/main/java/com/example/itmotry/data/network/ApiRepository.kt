package com.example.itmotry.data.network

import com.example.itmotry.API_KEY
import com.example.itmotry.PAGE_SIZE
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiRepository {
    @GET("everything?page={page}&pageSize=$PAGE_SIZE&apiKey=$API_KEY")
    suspend fun getAllNews(
        @Path("page") page: Int,
    ): NWArticlesResponse

    @GET("everything?q={query}&page={page}&pageSize=$PAGE_SIZE&apiKey=$API_KEY")
    suspend fun getQueryNews(
        @Path("query") query: String,
        @Path("page") page: Int,
    ): NWArticlesResponse
}
