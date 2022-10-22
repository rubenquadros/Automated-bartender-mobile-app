package com.ruben.bartender.domain.record

import com.google.firebase.auth.PhoneAuthCredential

/**
 * Created by Ruben Quadros on 23/10/22
 **/
sealed class SendOtpRecord {
    data class VerificationSuccessRecord(val credential: PhoneAuthCredential) : SendOtpRecord()
    data class OtpVerificationId(val id: String) : SendOtpRecord()
}

data class SendOtpErrorRecord(
    val message: String
)
