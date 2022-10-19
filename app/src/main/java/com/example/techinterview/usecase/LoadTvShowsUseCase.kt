package com.example.techinterview.usecase

import com.example.techinterview.domain.wrapper.RepositoryResponseWrapper

interface LoadTvShowsUseCase {
    suspend fun loadTvShows(): RepositoryResponseWrapper
}