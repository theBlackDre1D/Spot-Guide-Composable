package com.g3.spot_guide.providers

import android.content.Context
import co.bluecrystal.core.uiState.UIState
import com.g3.spot_guide.Session
import com.g3.spot_guide.enums.FirestoreEntityName
import com.g3.spot_guide.models.ImageModel
import com.g3.spot_guide.models.MemberRequestDecision
import com.g3.spot_guide.models.TodaySpot
import com.g3.spot_guide.models.User
import com.g3.spot_guide.utils.ImageCompressorUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.tasks.await
import java.util.*

class UserFirestoreProvider : BaseFirestoreProvider(FirestoreEntityName.USERS) {

    private suspend fun loginUserWithFirebase(email: String, password: String): FirebaseUser? {
        return try {
            val firebaseAuth = FirebaseAuth.getInstance()
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            result.user
        } catch (e: Exception) {
            null
        }
    }

    suspend fun loginUserWithFirebaseAsFlow(email: String, password: String): Flow<UIState<FirebaseUser>> {
        return flow {
            try {
                emit(UIState.Loading)

                val firebaseAuth = FirebaseAuth.getInstance()
                val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
                val firebaseUser = result.user
                if (firebaseUser != null) {
                    emit(UIState.Success(firebaseUser))
                } else {
                    emit(UIState.Error("No user found"))
                }
            } catch (e: Exception) {
                emit(UIState.Error(e.localizedMessage))
            }
        }
    }

    suspend fun registerUserWithFirebase(email: String, password: String, userName: String): Flow<UIState<FirebaseUser>> {
        return flow {
            try {
                emit(UIState.Loading)

                val firebaseAuth = FirebaseAuth.getInstance()
                val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
                val firebaseUser = result.user
                if (firebaseUser != null) {
                    val newUser = User(nick = userName, email = email)
                    val userCreationResult = createUser(newUser).last()
                    if (userCreationResult.getValueOrNull() != null) {
                        Session.saveAndSetLoggedInUser(newUser)
                        val userLoginResult = loginUserWithFirebase(email, password)
                        if (userLoginResult != null) {
                            emit(UIState.Success(userLoginResult))
                        } else {
                            emit(UIState.Error("No user found"))
                        }
                    } else {
                        emit(UIState.Error("User was not created"))
                    }
                } else {
                    emit(UIState.Error("User was not created"))
                }
            } catch (e: Exception) {
                emit(UIState.Error(e.localizedMessage))
            }
        }
    }

    suspend fun getUserByEmail(email: String): Flow<UIState<User>> {
        return flow {
            try {
                emit(UIState.Loading)

                val result = collectionReference.whereEqualTo("email", email).get().await()
                val users = result.toObjects(User::class.java)
                if (users.isNotEmpty()) {
                    emit(UIState.Success(users.first()))
                } else {
                    emit(UIState.Error("User not found"))
                }
            } catch (e: Exception) {
                emit(UIState.Error(e.localizedMessage))
            }
        }
    }

    suspend fun getUserById(userId: String): Flow<UIState<User>> {
        return flow {
            try {
                emit(UIState.Loading)

                val result = collectionReference.document(userId).get().await()
                val user = result.toObject(User::class.java)
                if (user != null) {
                    emit(UIState.Success(user))
                } else {
                    emit(UIState.Error("No user found"))
                }
            } catch (e: Exception) {
                emit(UIState.Error(e.localizedMessage))
            }
        }
    }

    suspend fun saveUser(user: User): Flow<UIState<User>> {
        return flow {
            try {
                emit(UIState.Loading)

                collectionReference.document(user.id).set(user).await()
                emit(UIState.Success(user))
            } catch (e: Exception) {
                emit(UIState.Error(e.localizedMessage))
            }
        }
    }

    suspend fun createUser(user: User): Flow<UIState<User>> {
        return flow {
            try {
                emit(UIState.Loading)

                collectionReference.document().set(user.toUploadModel()).await()
                emit(UIState.Success(user))
            } catch (e: Exception) {
                emit(UIState.Error(e.localizedMessage))
            }
        }
    }

    suspend fun changeProfilePicture(context: Context, newProfilePicture: ImageModel, currentProfilePicturePath: String): Flow<UIState<String>> {
        return flow {
            try {
                emit(UIState.Loading)

                val compressedImage = ImageCompressorUtils.compressImage(context, newProfilePicture)
                if (compressedImage != null) {
                    val storageRef = storage.reference
                    val storageReferenceString = "profile_pictures/${UUID.randomUUID()}"
                    val imageReference = storageRef.child(storageReferenceString)
                    val uploadTask = imageReference.putBytes(compressedImage.readBytes())
                    uploadTask.await()

                    try {
                        storageRef.child(currentProfilePicturePath).delete().await() // deleting old profile picture
                    } catch (e: Exception) {}

                    emit(UIState.Success(storageReferenceString))
                } else {
                    emit(UIState.Error("Image was not successfully compressed"))
                }
            } catch (e: Exception) {
                emit(UIState.Error(e.localizedMessage))
            }
        }
    }

    suspend fun addTodaySpotToCurrentUser(newTodaySpot: TodaySpot): Flow<UIState<TodaySpot>> {
        return flow {
            try {
                emit(UIState.Loading)

                val currentUser = Session.loggedInUser
                if (currentUser != null) {
                    val userToUpload = currentUser.copy(todaySpot = newTodaySpot)
                    collectionReference.document(currentUser.id).set(userToUpload).await()
                    emit(UIState.Success(newTodaySpot))
                } else {
                    emit(UIState.Error("No logged in user found"))
                }
            } catch (e: Exception) {
                emit(UIState.Error(e.localizedMessage))
            }
        }
    }

    suspend fun deleteTodaySpot(): Flow<UIState<Unit>> {
        return flow {
            try {
                emit(UIState.Loading)

                val currentUser = Session.loggedInUser
                if (currentUser != null) {
                    val userToUpload = currentUser.copy(todaySpot = null)
                    collectionReference.document(currentUser.id).set(userToUpload).await()
                    emit(UIState.Success(Unit))
                } else {
                    emit(UIState.Error("No logged in user found"))
                }
            } catch (e: Exception) {
                emit(UIState.Error(e.localizedMessage))
            }
        }
    }

    suspend fun onCrewMemberRequestDecision(accept: Boolean, requestUserId: String): Flow<UIState<MemberRequestDecision>> {
        return flow {
            try {
                emit(UIState.Loading)

                val otherUser = getUserById(requestUserId).last().getValueOrNull()
                val currentUser = Session.loggedInUser
                if (currentUser != null && otherUser != null) {
                    if (accept) {
                        val remainingMemberRequests = currentUser.memberRequests.filter { it != requestUserId }
                        val currentUserNewCrewMemberList = mutableListOf<String>()
                        currentUserNewCrewMemberList.addAll(currentUser.friends)
                        currentUserNewCrewMemberList.add(requestUserId)
                        val currentUserWithChanges = currentUser.copy(memberRequests = remainingMemberRequests, friends = currentUserNewCrewMemberList)

                        val otherUserCrewMembers = mutableListOf<String>()
                        otherUserCrewMembers.addAll(otherUser.friends)
                        otherUserCrewMembers.add(currentUser.id)
                        val otherUserWithChanges = otherUser.copy(friends = otherUserCrewMembers)

                        collectionReference.document(currentUserWithChanges.id).set(currentUserWithChanges).await()
                        collectionReference.document(otherUserWithChanges.id).set(otherUserWithChanges).await()

                        Session.saveAndSetLoggedInUser(currentUserWithChanges)
                        emit(UIState.Success(MemberRequestDecision(accept, requestUserId)))
                    } else {
                        emit(UIState.Error(null))
                    }
                } else {
                    emit(UIState.Error("Error during pairing user as crew member"))
                }
            } catch (e: Exception) {
                emit(UIState.Error("Error during pairing user as crew member"))
            }
        }
    }

    suspend fun getCrewMembersForThisSpot(spotId: String): Flow<UIState<List<User>>> {
        return flow {
            try {
                emit(UIState.Loading)

                val loggedUser = Session.loggedInUser
                if (loggedUser != null) {
                    val crewMembers = mutableListOf<User>()
                    loggedUser.friends.forEach { crewMemberId ->
                        val userEither = getUserById(crewMemberId).last()
                        userEither.getValueOrNull()?.let { user ->
                            crewMembers.add(user)
                        }
                    }

                    val crewMembersForSpot = crewMembers.filter { it.todaySpot?.spotId == spotId }

                    emit(UIState.Success(crewMembersForSpot))
                } else {
                    emit(UIState.Error("Logged user not found"))
                }
            } catch (e: Exception) {
                emit(UIState.Error(e.localizedMessage))
            }
        }
    }
}