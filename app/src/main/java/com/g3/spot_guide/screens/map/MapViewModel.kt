package com.g3.spot_guide.screens.map

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.g3.spot_guide.base.uiState.UIState
import com.g3.spot_guide.extensions.doInCoroutine
import com.g3.spot_guide.models.Spot
import com.g3.spot_guide.repositories.SpotRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import java.io.Serializable

class MapViewModel(
    private val spotRepository: SpotRepository
) : ViewModel() {

    data class ScreenState(
        val spots: List<Spot>
    ) : Serializable

    val screenState = MutableStateFlow<UIState<ScreenState>>(UIState.InitialValue)
    val spots = MutableLiveData<List<Spot>>(listOf())

    fun loadSpots() {
        doInCoroutine {
            spotRepository.getAllSpots().collect { uiState ->
                uiState.getValueOrNull()?.let { downloadedSpots ->
//                    screenState.emit(
//                        screenState.value.copy(spots = spots)
//                    )
                    spots.postValue(downloadedSpots)
                }
            }
        }
    }
}