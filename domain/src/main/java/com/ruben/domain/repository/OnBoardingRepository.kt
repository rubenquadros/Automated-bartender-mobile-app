package com.ruben.domain.repository

import com.google.firebase.auth.PhoneAuthCredential
import com.ruben.domain.model.OtpRecord
import com.ruben.domain.model.SignInRecord
import kotlinx.coroutines.flow.Flow

/**
 * Created by ruben.quadros on 09/03/20.
 **/
interface OnBoardingRepository {
  fun sendOTP(phoneNumber: String): Flow<OtpRecord?>
  fun signIn(phoneAuthCredential: PhoneAuthCredential): Flow<SignInRecord?>
}