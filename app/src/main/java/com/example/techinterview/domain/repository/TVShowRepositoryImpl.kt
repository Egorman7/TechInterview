package com.example.techinterview.domain.repository

import android.content.Context
import com.example.techinterview.R
import com.example.techinterview.domain.model.TVShowsHolder
import kotlinx.coroutines.delay
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlin.random.Random

class TVShowRepositoryImpl(private val context: Context) : TVShowRepository {
    override suspend fun loadTVShows(): TVShowsHolder {
        // fake delay
        delay(Random(System.currentTimeMillis()).nextLong(1000, 3000))

        val rawJson = context.resources.openRawResource(R.raw.data).reader().readText()
        return Json.decodeFromString(rawJson)
    }
}