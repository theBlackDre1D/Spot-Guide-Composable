package com.g3.spot_guide.screens.splash

import android.os.Bundle
import androidx.compose.material.ExperimentalMaterialApi
import com.g3.spot_guide.Session
import com.g3.spot_guide.base.activity.BaseActivity
import com.g3.spot_guide.screens.editProfile.EditProfileActivity
import com.google.firebase.auth.FirebaseAuth
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashActivity : BaseActivity<Nothing>() {

    private val splashActivityViewModel: SplashActivityViewModel by viewModel()

    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (FirebaseAuth.getInstance().currentUser != null) {
            val loggedUserEmail = FirebaseAuth.getInstance().currentUser?.email
            loggedUserEmail?.let { email ->
                splashActivityViewModel.user.observe(this) { userEither ->
                    val user = userEither.getValueOrNull()
                    if (user != null) {
                        Session.saveAndSetLoggedInUser(user)
                        if (!user.isUserValid) {
                            val parameters = EditProfileActivity.Parameters(user)
                            Session.application.coordinator.startEditProfileActivity(
                                this,
                                parameters,
                                true
                            )
                        } else {
//                            if (Prefs.getBoolean(SHOW_ON_BOARDING__PREFS_KEY, true)) {
//                                Session.application.coordinator.startOnBoardingActivity(this)
//                            } else {
                            Session.application.coordinator.startMainActivity(this)
//                            }
                        }
                    } else {
                        Session.application.coordinator.startLoginActivity(this)
                    }
                }

                splashActivityViewModel.getUser(email)
            }
        } else {
            Session.application.coordinator.startLoginActivity(this)
        }
    }
}