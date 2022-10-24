package com.ruben.bartender.data.remote.model.request

import com.google.firebase.auth.PhoneAuthProvider

/**
 * Created by ruben.quadros on 09/03/20.
 **/
data class SendOtpRequest(
  val phoneNumber: String,
  val resendToken: PhoneAuthProvider.ForceResendingToken?
)