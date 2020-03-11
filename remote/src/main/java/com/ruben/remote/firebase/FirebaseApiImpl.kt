package com.ruben.remote.firebase

import android.util.Log
import com.google.android.gms.tasks.TaskExecutors
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.ruben.remote.model.request.SendOtpRequest
import com.ruben.remote.model.request.SignInRequest
import com.ruben.remote.model.response.basicMenuResponse.BasicMenuResponse
import com.ruben.remote.model.response.menuCategoryResponse.CategoryResponse
import com.ruben.remote.model.response.onBoardingResponse.SendOtpResponse
import com.ruben.remote.model.response.onBoardingResponse.SignInResponse
import com.ruben.remote.utils.ApiConstants
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import java.util.concurrent.*

/**
 * Created by ruben.quadros on 01/03/20.
 **/
@Suppress("PrivatePropertyName", "NON_APPLICABLE_CALL_FOR_BUILDER_INFERENCE")
@ExperimentalCoroutinesApi
class FirebaseApiImpl : FirebaseApi {

  private val firestoreDB = FirebaseFirestore.getInstance()
  private val TAG = javaClass.simpleName

  override  fun getBasicMenu(): Flow<BasicMenuResponse?> {
    return channelFlow {
      firestoreDB.collection(ApiConstants.MENU_COLLECTION).document(ApiConstants.BASIC_MENU_DOC)
        .collection(ApiConstants.BASIC_MENU_COLLECTION)
        .get()
        .addOnSuccessListener { result ->
          val basicMenuResponse = BasicMenuResponse(result.documents)
          channel.offer(basicMenuResponse)
        }
        .addOnFailureListener {
          Log.d(TAG, it.toString())
          channel.offer(null)
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
          channel.offer(categoryResponse)
        }
        .addOnFailureListener{
          Log.d(TAG, it.toString())
          channel.offer(null)
          channel.close()
        }
      awaitClose()
    }
  }

  override fun sendOTP(sendOtpRequest: SendOtpRequest): Flow<SendOtpResponse?> {
    return channelFlow {
      PhoneAuthProvider.getInstance()
        .verifyPhoneNumber(
        sendOtpRequest.phoneNumber,
        ApiConstants.TIMEOUT_OTP,
        TimeUnit.SECONDS,
        TaskExecutors.MAIN_THREAD,
        object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
          override fun onVerificationCompleted(p0: PhoneAuthCredential) {
            val sendOtpResponse = SendOtpResponse(null, null, null, null, null)
            sendOtpResponse.phoneAuthCredential = p0
            channel.offer(sendOtpResponse)
          }

          override fun onVerificationFailed(p0: FirebaseException) {
            val sendOtpResponse = SendOtpResponse(null, null, null, null, null)
            sendOtpResponse.errorMessage = p0.message
            channel.offer(sendOtpResponse)
            channel.close(null)
          }

          override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
            super.onCodeSent(p0, p1)
            val sendOtpResponse = SendOtpResponse(null, null, null, null, null)
            sendOtpResponse.verificationID = p0
            sendOtpResponse.token = p1
            channel.offer(sendOtpResponse)
          }
        }
      )
      awaitClose()
    }
  }

  override fun signIn(signInRequest: SignInRequest): Flow<SignInResponse?> {
    return channelFlow {
      FirebaseAuth.getInstance().signInWithCredential(signInRequest.phoneAuthCredential)
        .addOnCompleteListener {
          val signInResponse = SignInResponse(0)
          if(it.isSuccessful) {
            signInResponse.status = 200
            channel.offer(signInResponse)
          }else {
            signInResponse.status = 401
            channel.offer(signInResponse)
          }
        }
        .addOnFailureListener{
          val signInResponse = SignInResponse(0)
          signInResponse.status = 500
          channel.offer(signInResponse)
          channel.close(null)
        }
      awaitClose()
    }
  }
}