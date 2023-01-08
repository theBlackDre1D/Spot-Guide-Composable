package co.bluecrystal.core.uiState

sealed class UIState<out T> {
    data class Error(val message: String?) : UIState<Nothing>()
    data class Success<T>(val value: T) : UIState<T>()
    object Loading : UIState<Nothing>()
    object InitialValue : UIState<Nothing>()

    fun getValueOrNull(): T? {
        return when (this) {
            is Error, Loading, InitialValue -> null
            is Success -> this.value
        }
    }
}