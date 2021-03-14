package com.g3.spot_guide.screens.map

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.g3.spot_guide.commonComposables.SpotGuideAppBar

@Composable
fun MapScreenUI() {

    val viewModel: MapViewModel = viewModel()
    val state = viewModel.screenState.observeAsState()

    Scaffold(
        topBar = { SpotGuideAppBar(middleText = "Map") },
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "This will be map screen")
    }
}