package com.ruben.data.repository

import com.ruben.data.DataSource
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
class OnBoardingRepositoryImpl @Inject constructor(dataSource: DataSource) : OnBoardingRepository {

  private val firebaseApi = dataSource.api().firebaseApiHandler()

  override fun sendOTP(sendOtpRequest: SendOtpRequest): Flow<SendOtpResponse?> {
    return firebaseApi.sendOTP(sendOtpRequest)
  }

  override fun signIn(signInRequest: SignInRequest): Flow<SignInResponse?> {
    return firebaseApi.signIn(signInRequest)
  }
}