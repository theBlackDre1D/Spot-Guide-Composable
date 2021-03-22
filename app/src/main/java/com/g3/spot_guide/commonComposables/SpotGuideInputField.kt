package com.g3.spot_guide.commonComposables

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.g3.spot_guide.R
import com.g3.spot_guide.screens.login.login.ui.theme.BurgundyPrimary
import com.g3.spot_guide.screens.splash.ui.theme.SpotGuideTheme


@Composable
fun StyledInputField(value: String, @DrawableRes iconResId: Int, @StringRes hint: Int? = null, securedInput: Boolean = false, onValueChange: (String) -> Unit) {
    Surface(
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp)
            .background(Color.White)
    ) {
        TextField(
            value = value,
            colors = styledInputFieldColors(),
            singleLine = true,
            placeholder = {
                hint?.let { hintResId ->
                    Text(text = stringResource(id = hintResId))
                }
            },
            visualTransformation = if (securedInput) PasswordVisualTransformation() else VisualTransformation.None,
            leadingIcon = {
                Icon(painterResource(id = iconResId), contentDescription = null)
            },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White),
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
        StyledInputField("Test text", R.drawable.ic_account, securedInput = true, onValueChange = {})
    }
}