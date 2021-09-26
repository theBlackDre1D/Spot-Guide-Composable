package com.g3.spot_guide.screens.onBoarding

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.g3.spot_guide.composables.Pager
import com.g3.spot_guide.models.OnBoardPagerScreen


@Composable
fun OnBoardingActivityUI() {
    val pagerScreens = listOf(
        OnBoardPagerScreen("Prva obrazovka"),
        OnBoardPagerScreen("Druha obrazovka"),
        OnBoardPagerScreen("Tretia obrazovka"),
        OnBoardPagerScreen("Stvrta obrazovka")
    )

    Pager(
        items = pagerScreens,
        orientation = Orientation.Horizontal,
        itemSpacing = 10.dp
    ) {

    }
}