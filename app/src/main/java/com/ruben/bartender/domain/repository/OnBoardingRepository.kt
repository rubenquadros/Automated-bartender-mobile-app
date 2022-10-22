package com.ruben.bartender.domain.repository

import com.google.firebase.auth.PhoneAuthCredential
import com.ruben.bartender.domain.BaseRecord
import com.ruben.bartender.domain.record.ErrorRecord
import com.ruben.bartender.domain.record.LoginRecord
import com.ruben.bartender.domain.record.SendOtpErrorRecord
import com.ruben.bartender.domain.record.SendOtpRecord
import kotlinx.coroutines.flow.Flow

/**
 * Created by ruben.quadros on 09/03/20.
 **/
interface OnBoardingRepository {
  suspend fun sendOtp(phoneNumber: String): Flow<BaseRecord<SendOtpRecord, SendOtpErrorRecord>>
  suspend fun login(phoneAuthCredential: PhoneAuthCredential): Flow<BaseRecord<LoginRecord, ErrorRecord>>
}