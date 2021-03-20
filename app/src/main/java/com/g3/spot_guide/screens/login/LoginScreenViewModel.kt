package com.g3.spot_guide.screens.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.g3.spot_guide.base.either.Either
import com.g3.spot_guide.extensions.doInCoroutine
import com.g3.spot_guide.repositories.UserRepository
import com.google.firebase.auth.FirebaseUser
import java.io.Serializable

class LoginScreenViewModel(
    private val repository: UserRepository
) : ViewModel() {

    val state = MutableLiveData<State>()

    data class State(
        var email: String = "",
        var password: String = ""
    ) : Serializable

    val loggedInUser = MutableLiveData<Either<FirebaseUser>>()

    fun logIn() {
        doInCoroutine {
            val result = repository.loginUserWithFirebase(state.value?.email ?: "", state.value?.password ?: "")
            loggedInUser.postValue(result)
        }
    }
}