package com.example.itmotry.di

import android.content.Context
import android.content.SharedPreferences
import com.example.itmotry.API_ENDPOINT
import com.example.itmotry.CONNECT_TIMEOUT
import com.example.itmotry.READ_TIMEOUT
import com.example.itmotry.WRITE_TIMEOUT
import com.example.itmotry.data.domain.NewsRepository
import com.example.itmotry.data.domain.NewsRepositoryImpl
import com.example.itmotry.data.network.ApiRepository
import com.example.itmotry.ui.main.MainScreenViewModel
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

fun initKoin(appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(
            repositoryModule,
            viewModelsModule,
        )
    }

val viewModelsModule = module {
    viewModel { MainScreenViewModel(get()) }
}

val repositoryModule = module {

    single { provideSharedPreferences(androidContext()) }
    single { provideOkHttpClient() }
    single { provideRetrofit(get()) }
    single { provideApiService(get()) }
    single<NewsRepository> {
        NewsRepositoryImpl(apiRepository = get())
    }
}


private fun provideSharedPreferences(context: Context): SharedPreferences {
    return context.getSharedPreferences("APP_SHARED_PREFERENCES", Context.MODE_PRIVATE)
}

private fun provideOkHttpClient(): OkHttpClient {
    return OkHttpClient.Builder()
        .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
        .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
        .build()
}

private fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(API_ENDPOINT)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

private fun provideApiService(retrofit: Retrofit): ApiRepository {
    return retrofit.create(ApiRepository::class.java)
}
