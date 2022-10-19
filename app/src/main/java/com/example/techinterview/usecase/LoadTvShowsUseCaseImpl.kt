package com.example.techinterview.usecase

import com.example.techinterview.domain.repository.TVShowRepository
import com.example.techinterview.domain.wrapper.RepositoryResponseWrapper

class LoadTvShowsUseCaseImpl(private val tvShowRepository: TVShowRepository) : LoadTvShowsUseCase {
    override suspend fun loadTvShows(): RepositoryResponseWrapper {
        return try {
            RepositoryResponseWrapper.Success(tvShowRepository.loadTVShows().shows.at.distinct())
        } catch (e: Exception){
            e.printStackTrace()
            RepositoryResponseWrapper.Error(e.message ?: "Unknown error!")
        }
    }
}