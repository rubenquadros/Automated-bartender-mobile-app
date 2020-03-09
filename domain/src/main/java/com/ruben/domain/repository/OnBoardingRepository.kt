package com.ruben.domain.repository

import com.ruben.remote.model.request.SendOtpRequest
import com.ruben.remote.model.request.SignInRequest
import com.ruben.remote.model.response.onBoardingResponse.SendOtpResponse
import com.ruben.remote.model.response.onBoardingResponse.SignInResponse
import kotlinx.coroutines.flow.Flow

/**
 * Created by ruben.quadros on 09/03/20.
 **/
interface OnBoardingRepository {
  fun sendOTP(sendOtpRequest: SendOtpRequest): Flow<SendOtpResponse?>
  fun signIn(signInRequest: SignInRequest): Flow<SignInResponse?>
}