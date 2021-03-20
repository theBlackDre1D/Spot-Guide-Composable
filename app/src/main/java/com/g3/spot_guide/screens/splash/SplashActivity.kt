package com.g3.spot_guide.screens.splash

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.g3.spot_guide.Session
import com.g3.spot_guide.screens.splash.ui.theme.SpotGuideTheme
import com.google.firebase.auth.FirebaseAuth
import org.koin.android.viewmodel.ext.android.viewModel

class SplashActivity : ComponentActivity() {

    private val splashActivityViewModel: SplashActivityViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContent {
//            SpotGuideTheme {
//                // A surface container using the 'background' color from the theme
//                Surface(color = MaterialTheme.colors.background) {
//                    Greeting("Android")
//                }
//            }
//        }

        if (FirebaseAuth.getInstance().currentUser != null) {
            val loggedUserEmail = FirebaseAuth.getInstance().currentUser?.email
            loggedUserEmail?.let { email ->
                splashActivityViewModel.user.observe(this, { userEither ->
                    val user = userEither.getValueOrNull()
                    if (user != null) {
                        Session.saveAndSetLoggedInUser(user)

//                        if (!user.isUserValid) {
//                            val parameters = EditProfileActivity.Parameters(user)
//                            Session.application.coordinator.startEditProfileActivity(
//                                this,
//                                parameters,
//                                true
//                            )
//                        } else {
//                            if (Prefs.getBoolean(SHOW_ON_BOARDING__PREFS_KEY, true)) {
//                                Session.application.coordinator.startOnBoardingActivity(this)
//                            } else {
//                                Session.application.coordinator.startMapActivity(this)
//                            }
//                        }
                    } else {
                        Session.application.coordinator.startLoginActivity(this)
                    }
                })

                splashActivityViewModel.getUser(email)
            }
        } else {
            Session.application.coordinator.startLoginActivity(this)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview4() {
    SpotGuideTheme {
//        Greeting("Android")
    }
}