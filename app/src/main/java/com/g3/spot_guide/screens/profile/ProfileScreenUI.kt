package com.g3.spot_guide.screens.profile

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.g3.spot_guide.commonComposables.SpotGuideAppBar

@Composable
fun ProfileScreenUI() {
    Scaffold(
        topBar = { SpotGuideAppBar(middleText = "Profile") },
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "This will be profile screen")
    }
}