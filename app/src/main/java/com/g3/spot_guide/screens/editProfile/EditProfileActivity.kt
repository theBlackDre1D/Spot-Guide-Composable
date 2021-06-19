package com.g3.spot_guide.screens.editProfile

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.g3.spot_guide.base.activity.BaseActivity
import com.g3.spot_guide.base.interfaces.BaseParameters
import com.g3.spot_guide.models.User
import com.g3.spot_guide.screens.editProfile.ui.theme.SpotGuideTheme
import org.koin.android.viewmodel.ext.android.viewModel
import java.io.Serializable

const val EDIT_PROFILE_PARAMETERS__EXTRAS_KEY = "EDIT_PROFILE_PARAMETERS__EXTRAS_KEY"

class EditProfileActivity : BaseActivity<EditProfileActivity.Arguments>() {

    private val editProfileActivityViewModel: EditProfileActivityViewModel by viewModel()

    inner class Arguments : BaseParameters {
        override fun loadParameters(extras: Bundle) {
            editProfileActivityViewModel.activityArguments = extras.getSerializable(EDIT_PROFILE_PARAMETERS__EXTRAS_KEY) as Parameters
        }
    }

    data class Parameters(
        val user: User
    ) : Serializable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SpotGuideTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Greeting("Edit profile activity")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview5() {
    SpotGuideTheme {
        Greeting("Android")
    }
}