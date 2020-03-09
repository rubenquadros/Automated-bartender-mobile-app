package com.ruben.domain.interactor

import com.ruben.domain.repository.OnBoardingRepository
import com.ruben.remote.model.request.SendOtpRequest
import com.ruben.remote.model.request.SignInRequest
import com.ruben.remote.model.response.onBoardingResponse.SendOtpResponse
import com.ruben.remote.model.response.onBoardingResponse.SignInResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by ruben.quadros on 09/03/20.
 **/
class OnBoardingUseCase @Inject constructor(private val onBoardingRepository: OnBoardingRepository) {

  fun sendOTP(sendOtpRequest: SendOtpRequest): Flow<SendOtpResponse?> {
    return onBoardingRepository.sendOTP(sendOtpRequest)
  }

  fun signIn(signInRequest: SignInRequest): Flow<SignInResponse?> {
    return onBoardingRepository.signIn(signInRequest)
  }

}