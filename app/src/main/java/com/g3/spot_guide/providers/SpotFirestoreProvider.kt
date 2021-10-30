package com.g3.spot_guide.providers

import android.net.Uri
import com.g3.spot_guide.base.uiState.UIState
import com.g3.spot_guide.enums.FirestoreEntityName
import com.g3.spot_guide.models.Spot
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.io.File
import java.util.*

class SpotFirestoreProvider : BaseFirestoreProvider(FirestoreEntityName.SPOTS) {

    suspend fun getAllSpots(): Flow<UIState<List<Spot>>> {
        return flow {
            try {
                emit(UIState.Loading)
                val result = collectionReference.get().await()
                val spots = result.toObjects(Spot::class.java)
                emit(UIState.Success(spots))
            } catch (e: Exception) {
                emit(UIState.Error(null))
            }
        }
    }

    suspend fun uploadImages(images: List<File>): Flow<UIState<List<String>>> {
        return flow {
            try {
                emit(UIState.Loading)

                val storageRef = storage.reference
                val storageImagesReferences = mutableListOf<String>()
                images.forEach { imageFile ->
                    val storageReferenceString = "spot_images/${UUID.randomUUID()}"
                    val imageReference = storageRef.child(storageReferenceString)
                    val uploadTask = imageReference.putBytes(imageFile.readBytes())
                    uploadTask.await()
                    storageImagesReferences.add(storageReferenceString)
                }
                emit(UIState.Success(storageImagesReferences))
            } catch (e: Exception) {
                emit(UIState.Error(e.message))
            }
        }
    }

    suspend fun uploadSpot(spot: Spot): Flow<UIState<Unit>> {
        return flow {
            try {
                emit(UIState.Loading)

                collectionReference.document().set(spot.toUploadModel()).await()
                emit(UIState.Success(Unit))
            } catch (e: Exception) {
                emit(UIState.Error(e.message))
            }
        }
    }

    suspend fun downloadImage(imagePath: String): Flow<UIState<Uri>> {
        return flow {
            try {
                emit(UIState.Loading)

                val result = storage.reference.child(imagePath).downloadUrl.await()
                emit(UIState.Success(result))
            } catch (e: Exception) {
                emit(UIState.Error(e.message))
            }
        }
    }

    suspend fun getUsersSpots(userId: String): Flow<UIState<List<Spot>>> {
        return flow {
            try {
                emit(UIState.Loading)

                val result = collectionReference.whereEqualTo("authorId", userId).get().await()
                val spots = result.toObjects(Spot::class.java)
                emit(UIState.Success(spots))
            } catch (e: Exception) {
                emit(UIState.Error(e.message))
            }
        }
    }

    suspend fun getSpotById(spotId: String): Flow<UIState<Spot>> {
        return flow {
            try {
                emit(UIState.Loading)

                val result = collectionReference.document(spotId).get().await()
                val spot = result.toObject(Spot::class.java)
                if (spot != null) {
                    emit(UIState.Success(spot))
                } else {
                    emit(UIState.Error("Spot not found"))
                }
            } catch (e: Exception) {
                emit(UIState.Error(e.message))
            }
        }
    }
}