package com.g3.spot_guide.screens.login.login.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.g3.spot_guide.R
import com.g3.spot_guide.base.uiState.UIState
import com.g3.spot_guide.commonComposables.StyledInputField
import com.g3.spot_guide.commonComposables.greatVibesFontFamily
import com.g3.spot_guide.providers.UserFirestoreProvider
import com.g3.spot_guide.repositories.UserRepository
import com.g3.spot_guide.screens.login.login.ui.theme.Black333333
import com.g3.spot_guide.screens.login.login.ui.theme.BurgundyPrimary
import com.g3.spot_guide.screens.splash.ui.theme.SpotGuideTheme

@Composable
fun LoginActivityUI(loginScreenViewModel: LoginScreenViewModel, handler: LoginActivity.LoginScreenHandler) {

    val screenState = loginScreenViewModel.state.observeAsState(LoginScreenViewModel.State())
    val loginState = loginScreenViewModel.loggedInUser.collectAsState(initial = UIState.InitialValue)

    if (loginState.value is UIState.Success) {
        handler.fromLoginScreenToHomeScreen()
    }

    Scaffold(
        snackbarHost = {
            if (loginState.value is UIState.Error) {
                Text(text = stringResource(id = R.string.error__log_in))
            }
        }
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight(), contentAlignment = Alignment.Center
        ) {
            if (!screenState.value.loginLoading) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(R.string.app_name),
                        fontSize = 45.sp,
                        fontFamily = greatVibesFontFamily,
                        color = BurgundyPrimary
                    )

                    Spacer(modifier = Modifier.height(50.dp))

                    StyledInputField(
                        value = screenState.value.email,
                        iconResId = R.drawable.ic_email,
                        hint = R.string.login__email,
                        onValueChange = {
                            loginScreenViewModel.state.value = screenState.value.copy(email = it)
                        }
                    )

                    Spacer(modifier = Modifier.height(15.dp))

                    StyledInputField(
                        value = screenState.value.password,
                        iconResId = R.drawable.ic_password,
                        hint = R.string.login__password,
                        securedInput = true,
                        onValueChange = {
                            loginScreenViewModel.state.value = screenState.value.copy(password = it)
                        }
                    )

                    Spacer(modifier = Modifier.height(40.dp))

                    Button(onClick = {
                        loginScreenViewModel.logIn()
                    }, enabled = screenState.value.password.length >= 6,
                        shape = CircleShape) {
                        Text(text = stringResource(id = R.string.login__login), fontSize = 16.sp)
                    }

                    Spacer(modifier = Modifier.height(40.dp))

                    TextButton(
                        onClick = { handler.fromLoginScreenToRegisterScreen() },
                        shape = CircleShape) {
                        Text(text = stringResource(id = R.string.login__no_account), fontSize = 14.sp, color = Black333333)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = stringResource(id = R.string.login__register), fontSize = 14.sp, color = BurgundyPrimary)
                    }
                }
            } else {
                CircularProgressIndicator(
                    color = BurgundyPrimary
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = false, uiMode = Configuration.UI_MODE_TYPE_NORMAL)
@Composable
fun DefaultPreview4() {
    SpotGuideTheme {
        val handler = object : LoginActivity.LoginScreenHandler {
            override fun fromLoginScreenToRegisterScreen() {}
            override fun fromLoginScreenToHomeScreen() {}
        }

        LoginActivityUI(LoginScreenViewModel(UserRepository(UserFirestoreProvider())), handler)
    }
}