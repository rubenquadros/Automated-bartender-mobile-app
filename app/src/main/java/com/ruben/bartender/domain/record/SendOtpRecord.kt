package com.ruben.bartender.domain.record

import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken

/**
 * Created by Ruben Quadros on 23/10/22
 **/
sealed class SendOtpRecord {
    data class VerificationSuccessRecord(val credential: PhoneAuthCredential) : SendOtpRecord()
    data class OtpVerificationId(val id: String, val resendToken: ForceResendingToken) : SendOtpRecord()
}

data class SendOtpErrorRecord(
    val message: String
)
