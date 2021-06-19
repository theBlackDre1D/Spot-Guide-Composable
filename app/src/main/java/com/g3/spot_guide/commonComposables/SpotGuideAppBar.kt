package com.g3.spot_guide.commonComposables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.g3.spot_guide.R
import com.g3.spot_guide.screens.main.theme.BurgundyPrimary
import com.g3.spot_guide.screens.main.theme.SpotGuideTheme

fun Modifier.appBarSize(): Modifier {
    return this
        .fillMaxWidth()
        .height(50.dp)
}
@Composable
fun SpotGuideAppBar(middleText: String) {
    TopAppBar(
        Modifier.appBarSize(),
        backgroundColor = Color.White
    ) {
        Box(
            Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = middleText,
                fontSize = 24.sp,
                fontFamily = montserratMediumFontFamily,
                color = BurgundyPrimary,
                fontStyle = FontStyle.Normal
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    SpotGuideTheme {
        SpotGuideAppBar(stringResource(id = R.string.app_name))
    }
}