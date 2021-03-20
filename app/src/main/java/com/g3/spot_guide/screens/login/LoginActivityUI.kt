package com.g3.spot_guide.screens.login

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.g3.spot_guide.R
import com.g3.spot_guide.commonComposables.StyledInputField
import com.g3.spot_guide.commonComposables.greatVibesFontFamily
import com.g3.spot_guide.providers.UserFirestoreProvider
import com.g3.spot_guide.repositories.UserRepository
import com.g3.spot_guide.screens.login.ui.theme.Black333333
import com.g3.spot_guide.screens.login.ui.theme.BurgundyPrimary
import com.g3.spot_guide.screens.splash.ui.theme.SpotGuideTheme

@Composable
fun LoginActivityUI(loginScreenViewModel: LoginScreenViewModel) {

    val screenState = loginScreenViewModel.state.observeAsState(LoginScreenViewModel.State())

    Scaffold {
        Box(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight(), contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.app_name),
                    fontSize = 50.sp,
                    fontFamily = greatVibesFontFamily,
                    color = BurgundyPrimary
                )
                
                Spacer(modifier = Modifier.height(50.dp))

                StyledInputField(
                    value = screenState.value.email,
                    onValueChange = {
                            loginScreenViewModel.state.postValue(screenState.value?.copy(email = it))
                    }
                )

                Spacer(modifier = Modifier.height(15.dp))

                StyledInputField(
                    value = screenState.value.password,
                    onValueChange = {
                            loginScreenViewModel.state.postValue(screenState.value?.copy(password = it))
                    }
                )

                Spacer(modifier = Modifier.height(20.dp))

                Button(onClick = { /*TODO*/ }, shape = CircleShape) {
                    Text(text = "Log in", fontSize = 16.sp)
                }

                Spacer(modifier = Modifier.height(40.dp))

                TextButton(onClick = { /*TODO*/ }, shape = CircleShape) {
                    Text(text = "Don't have an account?", fontSize = 14.sp, color = Black333333)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Register", fontSize = 14.sp, color = BurgundyPrimary)
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = false, uiMode = Configuration.UI_MODE_TYPE_NORMAL)
@Composable
fun DefaultPreview4() {
    SpotGuideTheme {
        LoginActivityUI(LoginScreenViewModel(UserRepository(UserFirestoreProvider())))
    }
}