package com.example.itmotry.data.network

import com.example.itmotry.API_KEY
import com.example.itmotry.PAGE_SIZE
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiRepository {
    @GET("everything")
    suspend fun getAllNews(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int = PAGE_SIZE,
        @Query("apiKey") apiKey: String = API_KEY,
        @Query("domains") domains: String = "techcrunch.com,thenextweb.com",
    ): NWArticlesResponse

    @GET("everything?q={query}&page={page}&pageSize=$PAGE_SIZE&apiKey=$API_KEY")
    suspend fun getQueryNews(
        @Query("query") query: String,
        @Query("page") page: Int,
    ): NWArticlesResponse
}
