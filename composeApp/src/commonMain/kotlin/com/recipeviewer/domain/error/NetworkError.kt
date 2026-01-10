package com.recipeviewer.domain.error

sealed class AppError(message: String, cause: Throwable? = null) : Exception(message, cause)

sealed class NetworkError(message: String, cause: Throwable? = null) : AppError(message, cause)

class NoInternet : NetworkError("No internet connection")
class Timeout : NetworkError("Request timed out")
class ServerError : NetworkError("Server error (e.g. 404)")
class UnknownHost : NetworkError("Cannot resolve host")

data class Unknown(override val message: String, override val cause: Throwable? = null) : AppError(message, cause)