package com.g3.spot_guide.screens.map

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.io.Serializable

class MapViewModel : ViewModel() {

    data class ScreenState(
        val spots: List<Any>
    ) : Serializable

    val screenState = MutableLiveData<ScreenState>()
}