package com.ruben.bartender.domain.interactor.boarding

import com.google.firebase.auth.PhoneAuthCredential
import com.ruben.bartender.domain.model.OtpRecord
import com.ruben.bartender.domain.model.SignInRecord
import com.ruben.bartender.domain.repository.OnBoardingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by ruben.quadros on 09/03/20.
 **/
class OnBoardingUseCase @Inject constructor(private val onBoardingRepository: OnBoardingRepository) {

  fun sendOTP(phoneNumber: String): Flow<OtpRecord?> {
    return onBoardingRepository.sendOTP(phoneNumber)
  }

  fun signIn(phoneAuthCredential: PhoneAuthCredential, phoneNumber: String): Flow<SignInRecord?> {
    return onBoardingRepository.signIn(phoneAuthCredential, phoneNumber)
  }
}