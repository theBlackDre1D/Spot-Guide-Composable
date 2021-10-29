package com.g3.spot_guide.screens.map

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import java.io.Serializable

class MapViewModel : ViewModel() {

    data class ScreenState(
        val spots: List<Any>
    ) : Serializable

    val screenState = MutableStateFlow<ScreenState>(ScreenState(listOf()))
}