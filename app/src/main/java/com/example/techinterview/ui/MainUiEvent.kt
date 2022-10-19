package com.example.techinterview.ui

import com.example.techinterview.domain.model.TVShow

sealed interface MainUiEvent {
    class TvShowsListEvent(val tvShows: List<TVShow>) : MainUiEvent
    class ErrorEvent(val errorMessage: String): MainUiEvent
}
