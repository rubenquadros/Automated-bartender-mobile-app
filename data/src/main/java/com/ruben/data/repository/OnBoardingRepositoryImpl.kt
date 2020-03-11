package com.ruben.data.repository

import com.google.firebase.auth.PhoneAuthCredential
import com.ruben.data.DataSource
import com.ruben.data.mapper.BoardingMapper
import com.ruben.data.utils.DataConstants
import com.ruben.domain.model.OtpRecord
import com.ruben.domain.model.SignInRecord
import com.ruben.domain.repository.OnBoardingRepository
import com.ruben.remote.model.request.SendOtpRequest
import com.ruben.remote.model.request.SignInRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Created by ruben.quadros on 09/03/20.
 **/
class OnBoardingRepositoryImpl @Inject constructor(dataSource: DataSource) : OnBoardingRepository {

  private val firebaseApi = dataSource.api().firebaseApiHandler()
  private val preferences = dataSource.preference()
  private val boardingMapper = BoardingMapper()

  override fun sendOTP(phoneNumber: String): Flow<OtpRecord?> {
    return firebaseApi.sendOTP(SendOtpRequest(phoneNumber)).map {
      boardingMapper.mapOtpResponse(it)
    }
  }

  override fun signIn(phoneAuthCredential: PhoneAuthCredential): Flow<SignInRecord?> {
    return firebaseApi.signIn(SignInRequest(phoneAuthCredential)).map {
      if(it?.status == DataConstants.HTTP_OK) {
        preferences.isLoggedIn = true
      }
      boardingMapper.mapSignInResponse(it)
    }
  }
}