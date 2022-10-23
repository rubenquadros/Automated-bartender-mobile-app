package com.ruben.bartender.data.remote.firebase

import android.util.Log
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.ruben.bartender.data.remote.model.request.GetUserDataRequest
import com.ruben.bartender.data.remote.model.request.SaveUserDetailsRequest
import com.ruben.bartender.data.remote.model.request.SendOtpRequest
import com.ruben.bartender.data.remote.model.request.SignInRequest
import com.ruben.bartender.data.remote.model.response.basicMenuResponse.BasicMenuResponse
import com.ruben.bartender.data.remote.model.response.menuCategoryResponse.CategoryResponse
import com.ruben.bartender.data.remote.model.response.onBoardingResponse.CheckUserResponse
import com.ruben.bartender.data.remote.model.response.onBoardingResponse.SaveUserDetailsResponse
import com.ruben.bartender.data.remote.model.response.onBoardingResponse.SendOtpResponse
import com.ruben.bartender.data.remote.model.response.onBoardingResponse.LoginResponse
import com.ruben.bartender.data.remote.model.response.signoutResponse.SignoutResponse
import com.ruben.bartender.data.remote.model.response.userDataResponse.UserDataResponse
import com.ruben.bartender.data.remote.utils.ApiConstants
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.channelFlow

/**
 * Created by ruben.quadros on 01/03/20.
 **/
class FirebaseApiImpl @Inject constructor() : FirebaseApi {

    companion object {
        private const val TAG = "FirebaseApiImpl"
    }

    private val firestoreDB = FirebaseFirestore.getInstance()

    override fun getBasicMenu(): Flow<BasicMenuResponse?> {
        return channelFlow {
            firestoreDB.collection(ApiConstants.MENU_COLLECTION)
                .document(ApiConstants.BASIC_MENU_DOC)
                .collection(ApiConstants.BASIC_MENU_COLLECTION)
                .get()
                .addOnSuccessListener { result ->
                    val basicMenuResponse = BasicMenuResponse(result.documents)
                    channel.trySend(basicMenuResponse)
                }
                .addOnFailureListener {
                    Log.d(TAG, it.message.toString())
                    channel.trySend(null)
                    channel.close(it)
                }
            awaitClose()
        }
    }

    override fun getMenuCategories(): Flow<CategoryResponse?> {
        return channelFlow {
            firestoreDB.collection(ApiConstants.MENU_COLLECTION).get()
                .addOnSuccessListener { result ->
                    val categoryResponse = CategoryResponse(result.documents)
                    channel.trySend(categoryResponse)
                }
                .addOnFailureListener {
                    Log.d(TAG, it.toString())
                    channel.trySend(null)
                    channel.close()
                }
            awaitClose()
        }
    }

    override suspend fun sendOtp(sendOtpRequest: SendOtpRequest): Flow<SendOtpResponse> {
        return callbackFlow {
            val firebaseAuth = FirebaseAuth.getInstance().apply { setLanguageCode("en") }

            val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                    trySend(SendOtpResponse.VerificationComplete(credential = credential))
                }

                override fun onVerificationFailed(exception: FirebaseException) {
                    trySend(SendOtpResponse.VerificationFailed(message = exception.message.orEmpty()))
                }

                override fun onCodeSent(
                    verificationId: String,
                    token: PhoneAuthProvider.ForceResendingToken
                ) {
                    super.onCodeSent(verificationId, token)
                    trySend(SendOtpResponse.OtpSent(verificationId = verificationId))
                }
            }

            val options = PhoneAuthOptions.newBuilder(firebaseAuth)
                .setPhoneNumber(sendOtpRequest.phoneNumber)
                .setTimeout(ApiConstants.TIMEOUT_OTP, TimeUnit.SECONDS)
                .setCallbacks(callbacks)
                .build()

            PhoneAuthProvider.verifyPhoneNumber(options)

            awaitClose()
        }
    }

    override suspend fun login(signInRequest: SignInRequest): Flow<LoginResponse> {
        return callbackFlow {
            FirebaseAuth.getInstance().signInWithCredential(signInRequest.phoneAuthCredential)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        val user = it.result?.user
                        if (user?.metadata?.creationTimestamp == user?.metadata?.lastSignInTimestamp
                            || (user?.metadata?.lastSignInTimestamp!! - user.metadata?.creationTimestamp!! < 200)
                        ) {
                            trySend(LoginResponse.NewUser)
                        } else {
                            trySend(LoginResponse.LoginSuccess)
                        }
                    } else {
                        trySend(LoginResponse.LoginFail(""))
                    }
                }
                .addOnFailureListener {
                    trySend(LoginResponse.LoginFail(message = it.message.orEmpty()))
                    close(null)
                }
            awaitClose()
        }
    }

    override suspend fun saveUser(saveUserDetailsRequest: SaveUserDetailsRequest): Flow<SaveUserDetailsResponse> {
        return callbackFlow {
            firestoreDB.collection(ApiConstants.USER_DETAILS_COLLECTION)
                .document(saveUserDetailsRequest.phoneNumber)
                .update(
                    ApiConstants.FIRST_NAME_DOC,
                    saveUserDetailsRequest.firstName,
                    ApiConstants.LAST_NAME_DOC,
                    saveUserDetailsRequest.lastName,
                    ApiConstants.PHONE_NUMBER_DOC,
                    saveUserDetailsRequest.phoneNumber
                )
                .addOnSuccessListener {
                    trySend(SaveUserDetailsResponse.SaveSuccess)
                }
                .addOnFailureListener {
                    trySend(SaveUserDetailsResponse.SaveFail(message = it.message.orEmpty()))
                    close(null)
                }
            awaitClose()
        }
    }

    override fun checkIfUserExists(): Flow<CheckUserResponse?> {
        return channelFlow {
            val checkUserResponse = CheckUserResponse(null)
            firestoreDB.collection(ApiConstants.USER_DETAILS_COLLECTION).get()
                .addOnSuccessListener {
                    checkUserResponse.users = it.documents
                    channel.trySend(checkUserResponse)
                }
                .addOnFailureListener {
                    channel.trySend(null)
                    channel.close()
                }
            awaitClose()
        }
    }

    override fun getUserData(getUserDataRequest: GetUserDataRequest): Flow<UserDataResponse?> {
        return channelFlow {
            firestoreDB.collection(ApiConstants.USER_DETAILS_COLLECTION)
                .document(getUserDataRequest.phoneNumber)
                .get()
                .addOnSuccessListener { result ->
                    val userDataResponse = UserDataResponse(result.data)
                    channel.trySend(userDataResponse)
                }
                .addOnFailureListener {
                    Log.d(TAG, it.message.toString())
                    channel.trySend(null)
                    channel.close(it)
                }
            awaitClose()
        }
    }

    override fun logout(): Flow<SignoutResponse?> {
        return channelFlow {
            val firebaseAuth = FirebaseAuth.getInstance()
            firebaseAuth.signOut()
            val signoutResponse = SignoutResponse(0)
            signoutResponse.status = ApiConstants.HTTP_OK
            channel.trySend(signoutResponse)
            awaitClose()
        }
    }
}