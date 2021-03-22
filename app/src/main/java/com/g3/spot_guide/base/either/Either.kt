package com.g3.spot_guide.base.either

sealed class Either<out T> {

    var isLoading = false

    data class Error(val message: String?) : Either<Nothing>() {
        constructor(exception: Exception) : this(exception.localizedMessage)
    }
    data class Success<T>(val value: T) : Either<T>()

    fun getValueOrNull(): T? {
        return when (this) {
            is Error -> null
            is Success -> this.value
        }
    }
}