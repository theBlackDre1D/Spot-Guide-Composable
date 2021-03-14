package com.g3.spot_guide.screens.settings

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.g3.spot_guide.commonComposables.SpotGuideAppBar

@Composable
fun SettingsScreenUI() {
    Scaffold(
        topBar = { SpotGuideAppBar(middleText = "Settings") },
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "This will be settings screen")
    }
}