package com.example.techinterview.domain.repository

import com.example.techinterview.domain.model.TVShowsHolder

interface TVShowRepository {
    suspend fun loadTVShows(): TVShowsHolder
}