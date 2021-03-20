package com.g3.spot_guide.screens.login

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.g3.spot_guide.providers.UserFirestoreProvider
import com.g3.spot_guide.repositories.UserRepository
import com.g3.spot_guide.screens.login.ui.theme.SpotGuideTheme
import org.koin.android.viewmodel.ext.android.viewModel

class LoginActivity : ComponentActivity() {

    private val loginScreenViewModel: LoginScreenViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SpotGuideTheme {
                LoginActivityUI(loginScreenViewModel)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview3() {
    SpotGuideTheme {
        LoginActivityUI(LoginScreenViewModel(UserRepository(UserFirestoreProvider())))
    }
}