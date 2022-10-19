package com.ruben.bartender.domain.model

import com.google.firebase.auth.PhoneAuthCredential

/**
 * Created by ruben.quadros on 10/03/20.
 **/
data class OtpRecord(
  var verificationID: String?,
  var errorMessage: String?,
  var credential: PhoneAuthCredential?
)