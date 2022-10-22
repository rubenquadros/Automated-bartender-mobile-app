package com.ruben.bartender.data.remote.model.response.onBoardingResponse

import com.google.firebase.auth.PhoneAuthCredential

/**
 * Created by ruben.quadros on 09/03/20.
 **/
sealed class SendOtpResponse {
    data class VerificationComplete(val credential: PhoneAuthCredential) : SendOtpResponse()
    data class VerificationFailed(val message: String) : SendOtpResponse()
    data class OtpSent(val verificationId: String) : SendOtpResponse()
}