package com.ruben.bartender.presentation.ui.login

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.PhoneAuthCredential
import com.ruben.bartender.domain.interactor.boarding.CheckUserUseCase
import com.ruben.bartender.domain.interactor.boarding.OnBoardingUseCase
import com.ruben.bartender.domain.model.CheckUserRecord
import com.ruben.bartender.domain.model.OtpRecord
import com.ruben.bartender.domain.model.SignInRecord
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
class LoginViewModel @Inject constructor(
  private val onBoardingUseCase: OnBoardingUseCase,
  private val checkUserUseCase: CheckUserUseCase
) : ViewModel(), LifecycleObserver {

  private var sendOtpResponse: MutableLiveData<OtpRecord?> = MutableLiveData()
  private var signInResponse: MutableLiveData<SignInRecord?> = MutableLiveData()
  private var checkUserExistsResponse: MutableLiveData<CheckUserRecord?> = MutableLiveData()

  fun sendOTP(phoneNumber: String) {
    viewModelScope.launch {
      onBoardingUseCase.sendOTP(phoneNumber).flowOn(Dispatchers.IO)
        .collect {
          sendOtpResponse.postValue(it)
        }
    }
  }

  fun signIn(phoneAuthCredential: PhoneAuthCredential, phoneNumber: String) {
    viewModelScope.launch {
      onBoardingUseCase.signIn(phoneAuthCredential, phoneNumber).flowOn(Dispatchers.IO)
        .collect {
          signInResponse.postValue(it)
        }
    }
  }

  fun checkIfUserExists(phoneNumber: String) {
    viewModelScope.launch {
      checkUserUseCase.checkIfUserExists(phoneNumber).flowOn(Dispatchers.IO)
        .collect {
          checkUserExistsResponse.postValue(it)
        }
    }
  }

  fun getOtpResponse(): LiveData<OtpRecord?> {
    return sendOtpResponse
  }

  fun getSignInResponse(): LiveData<SignInRecord?> {
    return signInResponse
  }

  fun getCheckUserResponse(): LiveData<CheckUserRecord?> {
    return checkUserExistsResponse
  }

}