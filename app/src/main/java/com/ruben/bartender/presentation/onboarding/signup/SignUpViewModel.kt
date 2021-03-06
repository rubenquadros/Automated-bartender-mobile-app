package com.ruben.bartender.presentation.onboarding.signup

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ruben.domain.interactor.boarding.SignUpUseCase
import com.ruben.domain.model.SaveUserRecord
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by ruben.quadros on 11/03/20.
 **/
@ExperimentalCoroutinesApi
class SignUpViewModel @Inject constructor(private val signUpUseCase: SignUpUseCase): ViewModel(),
  LifecycleObserver {

  private var saveUserResponse: MutableLiveData<SaveUserRecord?> = MutableLiveData()

  fun saveUser(firstName: String, lastName: String, phoneNumber: String) {
    viewModelScope.launch {
      signUpUseCase.saveUser(firstName, lastName, phoneNumber).flowOn(Dispatchers.IO)
        .collect { saveUserResponse.postValue(it) }
    }
  }

  fun getSaveUserResponse(): LiveData<SaveUserRecord?> {
    return saveUserResponse
  }
}