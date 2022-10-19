package com.ruben.bartender.data.repository

import com.google.firebase.auth.PhoneAuthCredential
import com.ruben.bartender.data.DataSource
import com.ruben.bartender.domain.model.CheckUserRecord
import com.ruben.bartender.domain.model.OtpRecord
import com.ruben.bartender.domain.model.SignInRecord
import com.ruben.bartender.domain.repository.OnBoardingRepository
import com.ruben.bartender.data.mapper.BoardingMapper
import com.ruben.bartender.data.remote.model.request.SendOtpRequest
import com.ruben.bartender.data.remote.model.request.SignInRequest
import com.ruben.bartender.data.remote.utils.ApiConstants
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

  override fun signIn(phoneAuthCredential: PhoneAuthCredential, phoneNumber: String): Flow<SignInRecord?> {
    return firebaseApi.signIn(SignInRequest(phoneAuthCredential)).map {
      if(it?.status == ApiConstants.HTTP_OK) {
        preferences.isLoggedIn = true
        preferences.phone = phoneNumber
      }else if(it?.status == ApiConstants.HTTP_NEW_USER) {
        preferences.phone = phoneNumber
      }
      boardingMapper.mapSignInResponse(it)
    }
  }

  override fun checkIfUserExists(phoneNumber: String): Flow<CheckUserRecord?> {
    return firebaseApi.checkIfUserExists().map {users->
      boardingMapper.mapCheckUserResponse(phoneNumber, users).also {
        if(it?.status == 200) {
          preferences.isRegistered = true
        }
      }
    }
  }
}