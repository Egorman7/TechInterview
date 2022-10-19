package com.example.techinterview.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class TVShowsListHolder(
    val at: List<TVShow>
)