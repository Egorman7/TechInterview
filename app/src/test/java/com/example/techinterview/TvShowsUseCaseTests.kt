package com.example.techinterview

import com.example.techinterview.domain.model.TVShow
import com.example.techinterview.domain.model.TVShowsHolder
import com.example.techinterview.domain.model.TVShowsListHolder
import com.example.techinterview.domain.repository.TVShowRepository
import com.example.techinterview.domain.wrapper.RepositoryResponseWrapper
import com.example.techinterview.usecase.LoadTvShowsUseCaseImpl
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class TvShowsUseCaseTests {
    @Test
    fun getTVShowsTest(){
        val repo = mock<TVShowRepository>{
            onBlocking {
                loadTVShows()
            } doReturn TVShowsHolder("", "", TVShowsListHolder(emptyList()))
        }

        val useCase = LoadTvShowsUseCaseImpl(repo)
        val tvShows = runBlocking {
            useCase.loadTvShows()
        }

        assert(tvShows is RepositoryResponseWrapper.Success<*>)
        assert(((tvShows as RepositoryResponseWrapper.Success<*>).data as List<TVShow>).isEmpty())
    }

    @Test
    fun getTVShowsDuplicateTest(){
        val someTvShow = TVShow("id", "url", 0L, 0L, "title", "episodeTitle")
        val repo = mock<TVShowRepository>{
            onBlocking {
                loadTVShows()
            } doReturn TVShowsHolder("", "", TVShowsListHolder(listOf(someTvShow, someTvShow)))
        }

        val useCase = LoadTvShowsUseCaseImpl(repo)
        val tvShows = runBlocking {
            useCase.loadTvShows()
        }

        assert(tvShows is RepositoryResponseWrapper.Success<*>)
        assert(((tvShows as RepositoryResponseWrapper.Success<*>).data as List<TVShow>).size == 1)
    }
}