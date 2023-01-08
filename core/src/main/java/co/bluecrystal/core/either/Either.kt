package co.bluecrystal.core.either

//sealed class Either<out T> {
//
//    var isLoading = false
//
//    data class Error(val message: String?) : Either<Nothing>() {
//        constructor(exception: Exception) : this(exception.localizedMessage)
//    }
//    data class Success<T>(val value: T) : Either<T>()
//    object Initial : Either<Nothing>()
//
//    fun getValueOrNull(): T? {
//        return when (this) {
//            is Error, Initial -> null
//            is Success -> this.value
//        }
//    }
//}