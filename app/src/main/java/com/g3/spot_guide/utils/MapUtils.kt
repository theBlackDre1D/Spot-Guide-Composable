package com.g3.spot_guide.utils

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.g3.spot_guide.R
import com.google.android.gms.maps.MapView


/**
 * COPIED FROM
 * https://github.com/joreilly/GalwayBus
 * https://medium.com/geekculture/google-maps-in-jetpack-compose-android-ae7b1ad84e9
 */

object MapUtils {

    @Composable
    fun rememberMapViewWithLifecycle(): MapView {
        val context = LocalContext.current
        val mapView = remember {
            MapView(context).apply {
                id = R.id.map
            }
        }

        // Makes MapView follow the lifecycle of this composable
        val lifecycleObserver = rememberMapLifecycleObserver(mapView)
        val lifecycle = LocalLifecycleOwner.current.lifecycle
        DisposableEffect(lifecycle) {
            lifecycle.addObserver(lifecycleObserver)
            onDispose {
                lifecycle.removeObserver(lifecycleObserver)
            }
        }

        return mapView
    }

    @Composable
    fun rememberMapLifecycleObserver(mapView: MapView): LifecycleEventObserver =
        remember(mapView) {
            LifecycleEventObserver { _, event ->
                when (event) {
                    Lifecycle.Event.ON_CREATE -> mapView.onCreate(Bundle())
                    Lifecycle.Event.ON_START -> mapView.onStart()
                    Lifecycle.Event.ON_RESUME -> mapView.onResume()
                    Lifecycle.Event.ON_PAUSE -> mapView.onPause()
                    Lifecycle.Event.ON_STOP -> mapView.onStop()
                    Lifecycle.Event.ON_DESTROY -> mapView.onDestroy()
                    else -> throw IllegalStateException()
                }
            }
        }
}