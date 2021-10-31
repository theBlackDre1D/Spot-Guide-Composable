package com.g3.spot_guide.screens.login.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.g3.spot_guide.base.either.Either
import com.g3.spot_guide.extensions.doInCoroutine
import com.g3.spot_guide.repositories.UserRepository
import com.google.firebase.auth.FirebaseUser
import java.io.Serializable

class RegisterScreenViewModel(
    private val repository: UserRepository
) : ViewModel() {

    val state = MutableLiveData(State())
    val registerResult = MutableLiveData<Either<FirebaseUser>>()

    data class State(
        val email: String = "",
        val password: String = "",
        val username: String = "",
        val registerLoading: Boolean = false
    ) : Serializable

    fun registerUser() {
        state.postValue(state.value?.copy(registerLoading = true))

        doInCoroutine {
            state.value?.let { state ->
                val result = repository.registerUserWithFirebase(state.email, state.password, state.username)
                registerResult.postValue(result)
            }
        }
    }
}