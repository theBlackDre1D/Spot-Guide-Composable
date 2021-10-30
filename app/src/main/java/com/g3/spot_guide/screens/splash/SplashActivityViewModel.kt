package com.g3.spot_guide.screens.splash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.g3.spot_guide.base.uiState.UIState
import com.g3.spot_guide.extensions.doInCoroutine
import com.g3.spot_guide.models.User
import com.g3.spot_guide.repositories.UserRepository
import kotlinx.coroutines.flow.map

class SplashActivityViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    val user = MutableLiveData<UIState<User>>()

    fun getUser(email: String) {
        doInCoroutine {
            userRepository.getUserByEmail(email).map {
                user.postValue(it)
            }
        }
    }
}