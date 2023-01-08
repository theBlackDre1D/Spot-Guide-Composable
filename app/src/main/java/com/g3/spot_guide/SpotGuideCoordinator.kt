package com.g3.spot_guide

import android.app.Activity
import android.content.Intent
import co.bluecrystal.core.coordinator.BaseCoordinator
import com.g3.spot_guide.screens.editProfile.EDIT_PROFILE_PARAMETERS__EXTRAS_KEY
import com.g3.spot_guide.screens.editProfile.EditProfileActivity
import com.g3.spot_guide.screens.login.login.ui.LoginActivity
import com.g3.spot_guide.screens.main.MainActivity
import com.g3.spot_guide.screens.onBoarding.OnBoardingActivity
import com.g3.spot_guide.screens.splash.SplashActivity

class SpotGuideCoordinator : BaseCoordinator() {

    fun startMainActivity(activity: Activity) {
        val intent = Intent(activity, MainActivity::class.java)
        super.startActivity(activity, intent, true)
    }

    fun startOnBoardingActivity(activity: Activity) {
        val intent = Intent(activity, OnBoardingActivity::class.java)
        super.startActivity(activity, intent, true)
    }

//    fun startAddSpotActivity(activity: Activity, parameters: AddSpotActivity.Parameters) {
//        val intent = Intent(activity, AddSpotActivity::class.java)
//        intent.putExtra(AddSpotActivity.ADD_SPOT_PARAMETERS__EXTRAS_KEY, parameters)
//        super.startActivity(activity, intent)
//    }
//
    fun startLoginActivity(activity: Activity) {
        val intent = Intent(activity, LoginActivity::class.java)
        super.startActivity(activity, intent, true)
    }

    fun startEditProfileActivity(activity: Activity, parameters: EditProfileActivity.Parameters, finishPreviousActivity: Boolean, ) {
        val intent = Intent(activity, EditProfileActivity::class.java)
        intent.putExtra(EDIT_PROFILE_PARAMETERS__EXTRAS_KEY, parameters)
        super.startActivity(activity, intent, finishPreviousActivity)
    }
//
//    fun startOtherUserProfileActivity(activity: Activity, parameters: OtherUserProfileActivity.Parameters) {
//        val intent = Intent(activity, OtherUserProfileActivity::class.java)
//        intent.putExtra(USER_PARAMETERS__EXTRAS_KEY, parameters)
//        super.startActivity(activity, intent)
//    }

    fun startSplashActivity(activity: Activity) {
        val intent = Intent(activity, SplashActivity::class.java)
        super.startActivity(activity, intent)
    }
}