package com.g3.spot_guide.screens.login.login.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.g3.spot_guide.base.uiState.UIState
import com.g3.spot_guide.extensions.doInCoroutine
import com.g3.spot_guide.repositories.UserRepository
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableStateFlow
import java.io.Serializable

class LoginScreenViewModel(
    private val repository: UserRepository
) : ViewModel() {

    val state = MutableLiveData(State())

    data class State(
        var email: String = "michal.gaborik.77@gmail.com",
        var password: String = "animatrix",
//        var email: String = "",
//        var password: String = "",
        var loginLoading: Boolean = false
    ) : Serializable

    val loggedInUser = MutableStateFlow<UIState<FirebaseUser>>(UIState.InitialValue)

    fun logIn() {
        state.value?.let {
            state.postValue(it.copy(loginLoading = true))
        }

        doInCoroutine {
            state.value?.let { stateValue ->
                val result = repository.loginUserWithFirebase(stateValue.email, stateValue.password)
                result.collect {
                    loggedInUser.emit(it)

                    if (it is UIState.Error) {
                        state.value = stateValue.copy(loginLoading = false)
                    }
                }
            }
        }
    }
}