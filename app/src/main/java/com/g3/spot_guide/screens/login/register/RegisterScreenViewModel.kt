package com.g3.spot_guide.screens.login.register

import androidx.lifecycle.ViewModel
import com.g3.spot_guide.base.uiState.UIState
import com.g3.spot_guide.extensions.doInCoroutine
import com.g3.spot_guide.repositories.UserRepository
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import java.io.Serializable

class RegisterScreenViewModel(
    private val repository: UserRepository
) : ViewModel() {

    val state = MutableStateFlow(State())
    val registerResult = MutableStateFlow<UIState<FirebaseUser>>(UIState.InitialValue)

    data class State(
        val email: String = "",
        val password: String = "",
        val username: String = "",
        val registerLoading: Boolean = false
    ) : Serializable

    fun registerUser() {
        state.value = state.value.copy(registerLoading = true)

        doInCoroutine {
            state.value.let { state ->
                repository.registerUserWithFirebase(state.email, state.password, state.username).map {
                    registerResult.value = it
                }
            }
        }
    }
}