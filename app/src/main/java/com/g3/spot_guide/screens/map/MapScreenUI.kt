package com.g3.spot_guide.screens.map

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.g3.spot_guide.R
import com.g3.spot_guide.base.uiState.UIState
import com.g3.spot_guide.commonComposables.SpotGuideAppBar
import com.g3.spot_guide.screens.main.theme.SpotGuideTheme
import com.g3.spot_guide.utils.MapUtils
import com.google.android.libraries.maps.CameraUpdateFactory
import com.google.android.libraries.maps.model.LatLng
import com.google.android.libraries.maps.model.MarkerOptions
import com.google.android.libraries.maps.model.PolylineOptions
import com.google.maps.android.ktx.awaitMap
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@Composable
fun MapScreenUI(viewModel: MapViewModel = getViewModel()) {

    val state = viewModel.screenState.collectAsState(initial = UIState.InitialValue)

    viewModel.loadSpots()

    Scaffold(
        topBar = { SpotGuideAppBar(middleText = stringResource(id = R.string.app_name)) },
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            val mapView = MapUtils.rememberMapViewWithLifecycle()

            AndroidView({ mapView }) { innerMapView ->
                CoroutineScope(Dispatchers.Main).launch {
                    val map = innerMapView.awaitMap()
                    map.uiSettings.isZoomControlsEnabled = false

                    val pickUp = LatLng(-35.016, 143.321)
                    val destination = LatLng(-32.491, 147.309)
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(destination, 6f))
                    val markerOptions = MarkerOptions()
                        .title("Sydney Opera House")
                        .position(pickUp)
                    map.addMarker(markerOptions)

                    val markerOptionsDestination = MarkerOptions()
                        .title("Restaurant Hubert")
                        .position(destination)
                    map.addMarker(markerOptionsDestination)

                    map.addPolyline(
                        PolylineOptions().add(
                            pickUp,
                            LatLng(-34.747, 145.592),
                            LatLng(-34.364, 147.891),
                            LatLng(-33.501, 150.217),
                            LatLng(-32.306, 149.248),
                            destination
                        )
                    )

//                    screenState.getValueOrNull()?.let {
//                        it.spots.forEach { spot ->
//                            val markerOptions = MarkerOptions()
//                                .title(spot.name)
//                                .position(spot.location)
//
//                            map.addMarker(markerOptions)
//                        }
//                    }
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

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SpotGuideTheme {
//        MapScreenUI()
    }
}