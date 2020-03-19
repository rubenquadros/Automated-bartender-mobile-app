package com.ruben.bartender.presentation.checkout

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ruben.domain.interactor.payment.CheckGooglePayUseCase
import com.ruben.domain.model.CheckGooglePayRecord
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by ruben.quadros on 19/03/20.
 **/
@ExperimentalCoroutinesApi
class PaymentViewModel @Inject constructor(private val checkGooglePayUseCase: CheckGooglePayUseCase) :
  ViewModel(), LifecycleObserver {

  private var checkGooglePayResponse: MutableLiveData<CheckGooglePayRecord?> = MutableLiveData()

  fun checkGooglePayAvailability(
    type: String,
    allowedCards: List<String>,
    allowedAuth: List<String>,
    apiVersion: Int,
    apiMinorVersion: Int
  ) {
    viewModelScope.launch {
      checkGooglePayUseCase.checkGooglePayAvailability(
          type,
          allowedCards,
          allowedAuth,
          apiVersion,
          apiMinorVersion
        ).flowOn(Dispatchers.IO)
        .collect {
          checkGooglePayResponse.postValue(it)
        }
    }
  }

  fun getCheckGoogleAvailabilityResponse(): LiveData<CheckGooglePayRecord?> {
    return checkGooglePayResponse
  }
}