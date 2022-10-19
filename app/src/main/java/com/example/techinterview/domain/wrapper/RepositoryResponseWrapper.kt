package com.example.techinterview.domain.wrapper

sealed interface RepositoryResponseWrapper {
    class Success<T: Any>(val data: T) : RepositoryResponseWrapper
    class Error(val message: String): RepositoryResponseWrapper
}