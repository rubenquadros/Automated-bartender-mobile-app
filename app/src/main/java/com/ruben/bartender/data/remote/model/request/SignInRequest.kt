package com.ruben.bartender.data.remote.model.request

import com.google.firebase.auth.PhoneAuthCredential

/**
 * Created by ruben.quadros on 10/03/20.
 **/
data class SignInRequest(
  var phoneAuthCredential: PhoneAuthCredential
)