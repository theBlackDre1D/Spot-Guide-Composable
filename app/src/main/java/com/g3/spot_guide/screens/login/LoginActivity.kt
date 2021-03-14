package com.g3.spot_guide.screens.login

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.g3.spot_guide.screens.login.ui.LoginActivityUI
import com.g3.spot_guide.screens.login.ui.theme.SpotGuideTheme

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SpotGuideTheme {
                LoginActivityUI()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview3() {
    SpotGuideTheme {
        LoginActivityUI()
    }
}