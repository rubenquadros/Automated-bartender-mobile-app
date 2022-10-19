package com.ruben.bartender.domain.repository

import com.google.firebase.auth.PhoneAuthCredential
import com.ruben.bartender.domain.model.CheckUserRecord
import com.ruben.bartender.domain.model.OtpRecord
import com.ruben.bartender.domain.model.SignInRecord
import kotlinx.coroutines.flow.Flow

/**
 * Created by ruben.quadros on 09/03/20.
 **/
interface OnBoardingRepository {
  fun sendOTP(phoneNumber: String): Flow<OtpRecord?>
  fun signIn(phoneAuthCredential: PhoneAuthCredential, phoneNumber: String): Flow<SignInRecord?>
  fun checkIfUserExists(phoneNumber: String): Flow<CheckUserRecord?>
}