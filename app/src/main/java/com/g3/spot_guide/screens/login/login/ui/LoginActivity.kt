package com.g3.spot_guide.screens.login.login.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.g3.spot_guide.Session
import com.g3.spot_guide.base.activity.BaseActivity
import com.g3.spot_guide.providers.UserFirestoreProvider
import com.g3.spot_guide.repositories.UserRepository
import com.g3.spot_guide.screens.login.login.ui.theme.SpotGuideTheme
import com.g3.spot_guide.screens.login.register.RegisterScreenUI
import com.g3.spot_guide.screens.login.register.RegisterScreenViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class LoginActivity : BaseActivity<Nothing>() {

    sealed class LoginActivityScreens(val route: String) {
        object LoginScreen : LoginActivityScreens("login")
        object RegisterScreen : LoginActivityScreens("register")
    }

    private val loginScreenViewModel: LoginScreenViewModel by viewModel()
    private val registerScreenViewModel: RegisterScreenViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SpotGuideTheme {
                val navController = rememberNavController()

                val loginScreenHandler = object : LoginScreenHandler {
                    override fun fromLoginScreenToRegisterScreen() {
                        navController.navigate(LoginActivityScreens.RegisterScreen.route)
                    }

                    override fun fromLoginScreenToHomeScreen() {
                        Session.application.coordinator.startMainActivity(this@LoginActivity)
                    }
                }

                val registerScreenHandler = object : RegisterScreenHandler {
                    override fun fromRegisterScreenToLoginScreen() {
                        navController.popBackStack()
                    }

                    override fun fromRegisterScreenToHomeScreen() {
                        Session.application.coordinator.startMainActivity(this@LoginActivity)
                    }
                }

                NavHost(navController, startDestination = LoginActivityScreens.LoginScreen.route) {
                    composable(LoginActivityScreens.LoginScreen.route, content = { LoginActivityUI(loginScreenViewModel, loginScreenHandler) })
                    composable(LoginActivityScreens.RegisterScreen.route, content = { RegisterScreenUI(registerScreenViewModel, registerScreenHandler) })
                }
            }
        }
    }

    interface LoginScreenHandler {
        fun fromLoginScreenToRegisterScreen()
        fun fromLoginScreenToHomeScreen()
    }

    interface RegisterScreenHandler {
        fun fromRegisterScreenToLoginScreen()
        fun fromRegisterScreenToHomeScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview3() {
    SpotGuideTheme {
        val handler = object : LoginActivity.LoginScreenHandler {
            override fun fromLoginScreenToRegisterScreen() {}
            override fun fromLoginScreenToHomeScreen() {}
        }

        LoginActivityUI(LoginScreenViewModel(UserRepository(UserFirestoreProvider())), handler)    }
}