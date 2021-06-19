package com.g3.spot_guide.screens.map

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.g3.spot_guide.R
import com.g3.spot_guide.commonComposables.SpotGuideAppBar
import com.g3.spot_guide.screens.main.theme.SpotGuideTheme

@Composable
fun MapScreenUI() {

    val viewModel: MapViewModel = viewModel()
    val state = viewModel.screenState.observeAsState()

    Scaffold(
        topBar = { SpotGuideAppBar(middleText = stringResource(id = R.string.app_name)) },
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "This will be map screen")
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SpotGuideTheme {
        MapScreenUI()
    }
}