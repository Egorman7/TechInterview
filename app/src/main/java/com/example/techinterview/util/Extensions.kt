package com.example.techinterview.util

import android.icu.text.SimpleDateFormat
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.time.Duration
import java.util.*

fun <T: Any> MutableLiveData<LiveDataEvent<T>>.postEvent(value: T) {
    postValue(LiveDataEvent(value))
}

fun <T: Any> AppCompatActivity.observeEventLiveData(liveData: LiveData<LiveDataEvent<T>>, handler: (T) -> Unit){
    liveData.observe(this) { it.handle(handler) }
}

fun Long.toHumanDate(): String {
    val dateTimeFormat = SimpleDateFormat("hh:mm:ss", Locale.getDefault())
    return dateTimeFormat.format(Date(this))
}

fun Long.toHumanDuration(): String {
    val duration = Duration.ofMillis(this)
    val totalSeconds = duration.seconds
    val h = totalSeconds / 3600
    val m = (totalSeconds % 3600) / 60
    val s = totalSeconds % 60
    return "%d:%02d:%02d".format(h, m ,s)
}