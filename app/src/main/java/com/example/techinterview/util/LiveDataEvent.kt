package com.example.techinterview.util

class LiveDataEvent<T: Any>(private val value: T) {
    private var isHandled = false

    fun handle(onNotHandled: T.() -> Unit){
        if(!isHandled){
            isHandled = true
            onNotHandled(value)
        }
    }

    fun getUnhandledValue(): T? {
        return if(!isHandled){
            isHandled = true
            value
        } else null
    }
}