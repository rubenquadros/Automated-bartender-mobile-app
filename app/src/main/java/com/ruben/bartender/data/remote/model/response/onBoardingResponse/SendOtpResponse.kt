package com.ruben.bartender.data.remote.model.response.onBoardingResponse

import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider

/**
 * Created by ruben.quadros on 09/03/20.
 **/
data class SendOtpResponse(
  var verificationID: String?,
  var token: PhoneAuthProvider.ForceResendingToken?,
  var errorMessage: String?,
  var phoneAuthCredential: PhoneAuthCredential?,
  var timeOut: String?
)