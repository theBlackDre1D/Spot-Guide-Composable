package com.g3.spot_guide.screens.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.g3.spot_guide.base.activity.BaseActivity
import com.g3.spot_guide.screens.main.theme.SpotGuideTheme

class MainActivity : BaseActivity<Nothing>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SpotGuideTheme {
                MainActivityUI()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SpotGuideTheme {
        MainActivityUI()
    }
}