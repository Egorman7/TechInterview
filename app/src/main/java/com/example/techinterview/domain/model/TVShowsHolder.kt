package com.example.techinterview.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class TVShowsHolder(
    val id: String,
    val type: String,
    val shows: TVShowsListHolder
)