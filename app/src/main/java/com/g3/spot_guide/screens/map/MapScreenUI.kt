package com.g3.spot_guide.screens.map

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.g3.spot_guide.R
import com.g3.spot_guide.base.uiState.UIState
import com.g3.spot_guide.commonComposables.SpotGuideAppBar
import com.g3.spot_guide.screens.main.theme.SpotGuideTheme
import com.google.android.gms.maps.GoogleMapOptions
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.Marker
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MapScreenUI(viewModel: MapViewModel = getViewModel()) {

    val state = viewModel.screenState.collectAsState(initial = UIState.InitialValue)
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(bottomSheetState = BottomSheetState(initialValue = BottomSheetValue.Collapsed))
    val coroutineScope = rememberCoroutineScope()

    BottomSheetScaffold(
        topBar = { SpotGuideAppBar(middleText = stringResource(id = R.string.app_name)) },
        modifier = Modifier.fillMaxSize(),
        sheetContent = {
            DateBottomSheet()
        },
        scaffoldState = bottomSheetScaffoldState,
        sheetShape = MaterialTheme.shapes.medium,
        sheetElevation = BottomSheetScaffoldDefaults.SheetPeekHeight,
        sheetBackgroundColor = Color.White,
        backgroundColor = Color.White,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {

            val singapore = LatLng(1.35, 103.87)
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                googleMapOptionsFactory = {
                    GoogleMapOptions().camera(CameraPosition.fromLatLngZoom(singapore, 12f))
                },
                properties = MapProperties(
                    isBuildingEnabled = true,
                    isIndoorEnabled = true,
                )
            ) {
                state.value.getValueOrNull()?.let { screenState ->
                    screenState.spots.forEach { spot ->
                        Marker(
                            position = spot.location,
                            title = spot.name,
                            snippet = spot.name,
                            onInfoWindowClick = {
                                coroutineScope.launch {
                                    bottomSheetScaffoldState.bottomSheetState.expand()
                                }
                            },
                        )
                    }
                }
            }

            Column(
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize(),
            ) {
                Button(
                    onClick = { },
                    modifier = Modifier.padding(10.dp),
                    shape = CircleShape
                ) {
                    Text(text = "Add point")
                }
            }
        }
    }
}

@Composable
fun DateBottomSheet() {
    SpotGuideTheme {
        val textColor = Color.Black

        Scaffold(
            backgroundColor = Color.White
        ) {
            Column() {
                Text(text = "ThisIsBottomSheet", color = textColor)
                Text(text = "ThisIsBottomSheet", color = textColor)
                Text(text = "ThisIsBottomSheet", color = textColor)
                Text(text = "ThisIsBottomSheet", color = textColor)
                Text(text = "ThisIsBottomSheet", color = textColor)
            }
        }
    }
}

@ExperimentalMaterialApi
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SpotGuideTheme {
        MapScreenUI()
    }
}