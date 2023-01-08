package com.g3.spot_guide.providers

import co.bluecrystal.core.uiState.UIState
import com.g3.spot_guide.enums.FirestoreEntityName
import com.g3.spot_guide.models.SpotReview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class ReviewFirestoreProvider : BaseFirestoreProvider(FirestoreEntityName.REVIEWS) {

    suspend fun getReviewsForSpot(spotId: String): Flow<UIState<List<SpotReview>>> {
        return flow {
            try {
                emit(UIState.Loading)

                val result = collectionReference.whereEqualTo("spotId", spotId).get().await()
                val reviews = result.toObjects(SpotReview::class.java)
                emit(UIState.Success(reviews))
            } catch (e: Exception) {
                emit(UIState.Error(e.localizedMessage))
            }
        }
    }

    suspend fun addReview(spotReview: SpotReview): Flow<UIState<SpotReview>> {
        return flow {
            try {
                emit(UIState.Loading)

                collectionReference.document().set(spotReview.toUploadModel()).await()
                emit(UIState.Success(spotReview))
            } catch (e: Exception) {
                emit(UIState.Error(e.localizedMessage))
            }
        }
    }
}