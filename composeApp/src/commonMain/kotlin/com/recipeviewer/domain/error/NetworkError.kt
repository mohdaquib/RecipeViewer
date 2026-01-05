package com.recipeviewer.domain.error

sealed interface AppError {
    data class Network(val message: String, val cause: Throwable? = null) : AppError
    data class NotFound(val message: String) : AppError
    data class Unknown(val message: String, val cause: Throwable?) : AppError
}

sealed interface NetworkError : AppError {
    data object NoInternet : NetworkError
    data object Timeout : NetworkError
    data object ServerError : NetworkError
    data object UnknownHost : NetworkError
}