package com.g3.spot_guide

import com.g3.spot_guide.providers.ReviewFirestoreProvider
import com.g3.spot_guide.providers.SpotFirestoreProvider
import com.g3.spot_guide.providers.UserFirestoreProvider
import com.g3.spot_guide.repositories.ImagesRepository
import com.g3.spot_guide.repositories.ReviewRepository
import com.g3.spot_guide.repositories.SpotRepository
import com.g3.spot_guide.repositories.UserRepository
import com.g3.spot_guide.screens.login.LoginScreenViewModel
import com.g3.spot_guide.screens.splash.SplashActivityViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModules = module {

    // ViewModels
    viewModel { SplashActivityViewModel( get() ) }
//    viewModel { AddSpotActivityViewModel() }
//    viewModel { AddSpotFragmentViewModel( get() ) }
//    viewModel { SpotDetailFragmentViewModel( get(), get(), get() ) }
//    viewModel { MapFragmentViewModel( get() ) }
//    viewModel { GalleryFragmentViewModel( get() ) }
//    viewModel { MapActivityViewModel( get() ) }
    viewModel { LoginScreenViewModel( get() ) }
//    viewModel { RegisterFragmentViewModel( get() ) }
//    viewModel { MyProfileFragmentViewModel( get(), get() ) }
//    viewModel { EditProfileActivityViewModel() }
//    viewModel { EditProfileFragmentViewModel( get() ) }
//    viewModel { CrewFragmentViewModel( get() ) }
//    viewModel { OtherUserProfileActivityViewModel() }
//    viewModel { OtherUserProfileFragmentViewModel( get(), get() ) }
//    viewModel { AddTodaySpotBottomSheetFragmentViewModel( get() ) }
//    viewModel { AddSpotReviewViewModel( get() ) }

    // Repositories
    single { SpotRepository( get() ) }
    single { ImagesRepository() }
    single { UserRepository( get() ) }
    single { ReviewRepository( get() ) }

    // Providers
    factory { SpotFirestoreProvider() }
    factory { UserFirestoreProvider() }
    factory { ReviewFirestoreProvider() }
}