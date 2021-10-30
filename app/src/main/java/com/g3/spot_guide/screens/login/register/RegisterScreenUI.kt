package com.g3.spot_guide.screens.login.register

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
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
import com.g3.spot_guide.screens.login.login.ui.LoginActivity
import com.g3.spot_guide.screens.login.login.ui.theme.Black333333
import com.g3.spot_guide.screens.login.login.ui.theme.BurgundyPrimary
import com.g3.spot_guide.screens.splash.ui.theme.SpotGuideTheme

@Composable
fun RegisterScreenUI(registerScreenViewModel: RegisterScreenViewModel, handler: LoginActivity.RegisterScreenHandler) {

    val screenState = registerScreenViewModel.state
    val registerState = registerScreenViewModel.registerResult

    if (registerState.value is UIState.Success) {
        handler.fromRegisterScreenToHomeScreen()
    }

    Scaffold(
        snackbarHost = {
            if (registerState.value is UIState.Error) {
                Text(text = stringResource(id = R.string.error__register))
            }
        }
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight(), contentAlignment = Alignment.Center
        ) {
            if (!screenState.value.registerLoading) {
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
                        value = screenState.value.username,
                        iconResId = R.drawable.ic_account,
                        hint = R.string.login__username,
                        onValueChange = {
                            registerScreenViewModel.state.value = screenState.value.copy(username = it)
                        }
                    )

                    Spacer(modifier = Modifier.height(15.dp))

                    StyledInputField(
                        value = screenState.value.email,
                        iconResId = R.drawable.ic_email,
                        hint = R.string.login__email,
                        onValueChange = {
                            registerScreenViewModel.state.value = screenState.value.copy(email = it)
                        }
                    )

                    Spacer(modifier = Modifier.height(15.dp))

                    StyledInputField(
                        value = screenState.value.password,
                        iconResId = R.drawable.ic_password,
                        hint = R.string.login__password,
                        securedInput = true,
                        onValueChange = {
                            registerScreenViewModel.state.value = screenState.value.copy(password = it)
                        }
                    )

                    Spacer(modifier = Modifier.height(40.dp))

                    Button(onClick = {
                        registerScreenViewModel.registerUser()
                    }, enabled = screenState.value.password.length >= 6,
                        shape = CircleShape
                    ) {
                        Text(text = stringResource(id = R.string.login__register), fontSize = 16.sp)
                    }

                    Spacer(modifier = Modifier.height(40.dp))

                    TextButton(
                        onClick = { handler.fromRegisterScreenToLoginScreen() },
                        shape = CircleShape
                    ) {
                        Text(text = stringResource(id = R.string.login__existing_account), fontSize = 14.sp, color = Black333333)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = stringResource(id = R.string.login__login), fontSize = 14.sp, color = BurgundyPrimary)
                    }
                }
            } else {
                CircularProgressIndicator(color = BurgundyPrimary)
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = false, uiMode = Configuration.UI_MODE_TYPE_NORMAL)
@Composable
fun DefaultPreview4() {
    SpotGuideTheme {
        val handler = object : LoginActivity.RegisterScreenHandler {
            override fun fromRegisterScreenToLoginScreen() {}
            override fun fromRegisterScreenToHomeScreen() {}
        }

        RegisterScreenUI(RegisterScreenViewModel(UserRepository(UserFirestoreProvider())), handler)
    }
}
