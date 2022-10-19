package com.example.techinterview.domain.model

import kotlinx.serialization.Serializable
import kotlin.math.abs

@Serializable
data class TVShow(
    val id: String,
    val imageUrl: String,
    val startTime: Long,
    val endTime: Long,
    val title: String,
    val episodeTitle: String
) {
    fun getDuration(): Long {
        return abs(endTime - startTime)
    }
}