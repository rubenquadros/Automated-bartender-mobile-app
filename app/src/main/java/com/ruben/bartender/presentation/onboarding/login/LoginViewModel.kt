package com.ruben.bartender.presentation.onboarding.login

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.PhoneAuthCredential
import com.ruben.domain.interactor.OnBoardingUseCase
import com.ruben.remote.model.request.SendOtpRequest
import com.ruben.remote.model.request.SignInRequest
import com.ruben.remote.model.response.onBoardingResponse.SendOtpResponse
import com.ruben.remote.model.response.onBoardingResponse.SignInResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by ruben.quadros on 09/03/20.
 **/
@ExperimentalCoroutinesApi
class LoginViewModel @Inject constructor(private val onBoardingUseCase: OnBoardingUseCase): ViewModel(), LifecycleObserver {

  private var sendOtpResponse: MutableLiveData<SendOtpResponse?> = MutableLiveData()
  private var signInResponse: MutableLiveData<SignInResponse?> = MutableLiveData()

  fun sendOTP(phoneNumber: String) {
    viewModelScope.launch {
      onBoardingUseCase.sendOTP(SendOtpRequest(phoneNumber)).flowOn(Dispatchers.IO)
        .collect {
          sendOtpResponse.postValue(it)
        }
    }
  }

  fun signIn(phoneAuthCredential: PhoneAuthCredential) {
    viewModelScope.launch {
      onBoardingUseCase.signIn(SignInRequest(phoneAuthCredential)).flowOn(Dispatchers.IO)
        .collect {
          signInResponse.postValue(it)
        }
    }
  }

  fun getOtpResponse(): LiveData<SendOtpResponse?> {
    return sendOtpResponse
  }

  fun getSignInResponse(): LiveData<SignInResponse?> {
    return signInResponse
  }

}