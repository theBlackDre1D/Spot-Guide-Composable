package com.g3.spot_guide.screens.onBoarding

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import co.bluecrystal.core.activity.BaseActivity
import com.g3.spot_guide.screens.onBoarding.ui.theme.SpotGuideTheme

const val SHOW_ON_BOARDING__PREFS_KEY = "SHOW_ON_BOARDING__PREFS_KEY"

class OnBoardingActivity : BaseActivity<Nothing>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SpotGuideTheme {
               OnBoardingActivityUI()
            }
        }
    }
}

@Composable
fun Greeting2(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview6() {
    SpotGuideTheme {
        Greeting2("Android")
    }
}