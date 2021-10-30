package com.g3.spot_guide.screens.login.login.ui

import androidx.lifecycle.ViewModel
import com.g3.spot_guide.base.uiState.UIState
import com.g3.spot_guide.extensions.doInCoroutine
import com.g3.spot_guide.repositories.UserRepository
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import java.io.Serializable

class LoginScreenViewModel(
    private val repository: UserRepository
) : ViewModel() {

    val state = MutableStateFlow(State())

    data class State(
        var email: String = "",
        var password: String = "",
        var loginLoading: Boolean = false
    ) : Serializable

    val loggedInUser = MutableStateFlow<UIState<FirebaseUser>>(UIState.InitialValue)

    fun logIn() {
        state.value = state.value.copy(loginLoading = true)

        doInCoroutine {
            val result = repository.loginUserWithFirebase(state.value.email, state.value.password)
            result.map {
                loggedInUser.value = it

                if (it is UIState.Error) {
                    state.value = state.value.copy(loginLoading = false)
                }
            }
        }
    }
}