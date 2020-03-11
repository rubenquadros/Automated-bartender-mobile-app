package com.ruben.bartender.presentation.onboarding.login

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.PhoneAuthCredential
import com.ruben.domain.interactor.boarding.OnBoardingUseCase
import com.ruben.domain.model.OtpRecord
import com.ruben.domain.model.SignInRecord
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

  private var sendOtpResponse: MutableLiveData<OtpRecord?> = MutableLiveData()
  private var signInResponse: MutableLiveData<SignInRecord?> = MutableLiveData()

  fun sendOTP(phoneNumber: String) {
    viewModelScope.launch {
      onBoardingUseCase.sendOTP(phoneNumber).flowOn(Dispatchers.IO)
        .collect {
          sendOtpResponse.postValue(it)
        }
    }
  }

  fun signIn(phoneAuthCredential: PhoneAuthCredential) {
    viewModelScope.launch {
      onBoardingUseCase.signIn(phoneAuthCredential).flowOn(Dispatchers.IO)
        .collect {
          signInResponse.postValue(it)
        }
    }
  }

  fun getOtpResponse(): LiveData<OtpRecord?> {
    return sendOtpResponse
  }

  fun getSignInResponse(): LiveData<SignInRecord?> {
    return signInResponse
  }

}