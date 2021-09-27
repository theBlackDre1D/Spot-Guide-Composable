package com.g3.spot_guide.screens.settings

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.g3.spot_guide.commonComposables.SpotGuideAppBar
import com.g3.spot_guide.screens.editProfile.ui.theme.SpotGuideTheme

@Composable
fun SettingsScreenUI() {
    Scaffold(
        topBar = { SpotGuideAppBar(middleText = "Settings") },
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "This will be settings screen")
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview5() {
    SpotGuideTheme {
        SettingsScreenUI()
    }
}