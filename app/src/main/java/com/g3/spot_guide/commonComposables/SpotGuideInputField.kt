package com.g3.spot_guide.commonComposables

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.g3.spot_guide.screens.login.ui.theme.BurgundyPrimary
import com.g3.spot_guide.screens.splash.ui.theme.SpotGuideTheme

private val textFieldShape = RoundedCornerShape(15.dp)

@Composable
fun StyledInputField(value: String, onValueChange: (String) -> Unit) {
    Surface(
//        elevation = 10.dp,
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp)
            .background(Color.White)
            .shadow(
                elevation = 5.dp,
                shape = textFieldShape
            )
    ) {
        TextField(
            value = value,
            colors = styledInputFieldColors(),
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, shape = textFieldShape),
            onValueChange = onValueChange
        )
    }
}

@Composable
private fun styledInputFieldColors(): TextFieldColors {
    return TextFieldDefaults.textFieldColors(
        backgroundColor = Color.White,
        cursorColor = BurgundyPrimary
    )
}

@Preview(showBackground = true, showSystemUi = false, uiMode = Configuration.UI_MODE_TYPE_NORMAL)
@Composable
fun DefaultPreview4() {
    SpotGuideTheme {
        StyledInputField("Test text", {})
    }
}