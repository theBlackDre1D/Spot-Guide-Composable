package com.g3.spot_guide.providers

import android.net.Uri
import com.g3.spot_guide.base.either.Either
import com.g3.spot_guide.enums.FirestoreEntityName
import com.g3.spot_guide.models.Spot
import kotlinx.coroutines.tasks.await
import java.io.File
import java.util.*

class SpotFirestoreProvider : BaseFirestoreProvider(FirestoreEntityName.SPOTS) {

    suspend fun getAllSpots(): Either<List<Spot>> {
        return try {
            val result = collectionReference.get().await()
            val spots = result.toObjects(Spot::class.java)
            Either.Success(spots)
        } catch (e: Exception) {
            Either.Error(null)
        }
    }

    suspend fun uploadImages(images: List<File>): Either<List<String>> {
        return try {
            val storageRef = storage.reference
            val storageImagesReferences = mutableListOf<String>()
            images.forEach { imageFile ->
                val storageReferenceString = "spot_images/${UUID.randomUUID()}"
                val imageReference = storageRef.child(storageReferenceString)
                val uploadTask = imageReference.putBytes(imageFile.readBytes())
                uploadTask.await()
                storageImagesReferences.add(storageReferenceString)
            }
            Either.Success(storageImagesReferences)
        } catch (e: Exception) {
            Either.Error(e.message)
        }
    }

    suspend fun uploadSpot(spot: Spot): Either<Unit> {
        return try {
            collectionReference.document().set(spot.toUploadModel()).await()
            Either.Success(Unit)
        } catch (e: Exception) {
            Either.Error(e.message)
        }
    }

    suspend fun downloadImage(imagePath: String): Either<Uri> {
        return try {
            val result = storage.reference.child(imagePath).downloadUrl.await()
            Either.Success(result)
        } catch (e: Exception) {
            Either.Error(null)
        }
    }

    suspend fun getUsersSpots(userId: String): Either<List<Spot>> {
        return try {
            val result = collectionReference.whereEqualTo("authorId", userId).get().await()
            val spots = result.toObjects(Spot::class.java)
            Either.Success(spots)
        } catch (e: Exception) { Either.Error(null) }
    }

    suspend fun getSpotById(spotId: String): Either<Spot> {
        return try {
            val result = collectionReference.document(spotId).get().await()
            val spot = result.toObject(Spot::class.java)
            if (spot != null) {
                Either.Success(spot)
            } else {
                Either.Error(null)
            }
        } catch (e: Exception) { Either.Error(null) }
    }
}