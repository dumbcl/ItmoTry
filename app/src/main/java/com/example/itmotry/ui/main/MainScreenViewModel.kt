package com.example.itmotry.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.itmotry.data.domain.NewsRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainScreenViewModel(
    private val newsRepository: NewsRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        MainScreenState(
            news = emptyList(),
            status = MainScreenState.LoadingStatus.LOADING,
            pagingStatus = MainScreenState.PagingStatus.SUCCESS,
            showedNewsIndex = null,
        )
    )

    val uiState = _uiState.asStateFlow()

    val currentPage = MutableStateFlow(1)

    fun load() = viewModelScope.launch {
        val news = async {
            newsRepository.getAllNews(currentPage.value)
        }.await()
        news.fold(
            onSuccess = { news ->
                _uiState.update {
                    uiState.value.copy(
                        news = news,
                        status = MainScreenState.LoadingStatus.SUCCESS
                    )
                }
                currentPage.update { currentPage.value + 1 }
            },
            onFailure = { error ->
                _uiState.update {
                    uiState.value.copy(
                        status = MainScreenState.LoadingStatus.ERROR
                    )
                }
            }
        )
    }

    fun retry() = viewModelScope.launch {
        val news = async {
            newsRepository.getAllNews(currentPage.value)
        }.await()
        news.fold(
            onSuccess = { news ->
                _uiState.update {
                    uiState.value.copy(
                        news = news,
                        status = MainScreenState.LoadingStatus.SUCCESS
                    )
                }
                currentPage.update { currentPage.value + 1 }
            },
            onFailure = { error ->
                _uiState.update {
                    uiState.value.copy(
                        status = MainScreenState.LoadingStatus.ERROR
                    )
                }
            }
        )
    }

    fun loadMore() = viewModelScope.launch {
        _uiState.update {
            uiState.value.copy(
                pagingStatus = MainScreenState.PagingStatus.LOADING
            )
        }
        val news = async {
            newsRepository.getAllNews(currentPage.value)
        }.await()
        news.fold(
            onSuccess = { news ->
                _uiState.update {
                    uiState.value.copy(
                        news = uiState.value.news + news,
                        pagingStatus = MainScreenState.PagingStatus.SUCCESS
                    )
                }
                currentPage.update { currentPage.value + 1 }
            },
            onFailure = { error ->
                _uiState.update {
                    uiState.value.copy(
                        pagingStatus = MainScreenState.PagingStatus.ERROR
                    )
                }
            }
        )
    }

    fun search(query: String) = viewModelScope.launch {
        currentPage.update { 1 }
        val news = async {
            newsRepository.getNewsByQuery(currentPage.value, query)
        }.await()
        news.fold(
            onSuccess = { news ->
                _uiState.update {
                    uiState.value.copy(
                        news = news,
                        status = MainScreenState.LoadingStatus.SUCCESS
                    )
                }
                currentPage.update { currentPage.value + 1 }
            },
            onFailure = { error ->
                _uiState.update {
                    uiState.value.copy(
                        status = MainScreenState.LoadingStatus.ERROR
                    )
                }
            }
        )
    }

    fun showNewsDialog(index: Int) {
        _uiState.update {
            uiState.value.copy(
                showedNewsIndex = index
            )
        }
    }

    fun closeNewsDialog() {
        _uiState.update {
            uiState.value.copy(
                showedNewsIndex = null,
            )
        }
    }
}
