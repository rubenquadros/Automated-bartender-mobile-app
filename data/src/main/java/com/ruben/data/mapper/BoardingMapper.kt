package com.ruben.data.mapper

import com.ruben.domain.model.OtpRecord
import com.ruben.domain.model.SignInRecord
import com.ruben.remote.model.response.onBoardingResponse.SendOtpResponse
import com.ruben.remote.model.response.onBoardingResponse.SignInResponse
import com.ruben.remote.utils.ApiConstants

/**
 * Created by ruben.quadros on 10/03/20.
 **/
class BoardingMapper {

  fun mapOtpResponse(sendOtpResponse: SendOtpResponse?): OtpRecord? {
    return if(sendOtpResponse != null) {
      val otpRecord = OtpRecord(null, null, null)
      when {
        sendOtpResponse.verificationID != null      -> {
          otpRecord.verificationID = sendOtpResponse.verificationID
        }
        sendOtpResponse.errorMessage != null        -> {
          otpRecord.errorMessage = sendOtpResponse.errorMessage
        }
        sendOtpResponse.phoneAuthCredential != null -> {
          otpRecord.credential = sendOtpResponse.phoneAuthCredential
        }
      }
      otpRecord
    }else {
      null
    }
  }

  fun mapSignInResponse(signInResponse: SignInResponse?): SignInRecord? {
    if(signInResponse != null) {
      val signInRecord = SignInRecord(0, "")
      when(signInResponse.status) {
        ApiConstants.HTTP_OK -> {
          signInRecord.responseCode = ApiConstants.HTTP_OK
          signInRecord.message = ApiConstants.SUCCESS
        }
        ApiConstants.HTTP_AUTH_FAIL -> {
          signInRecord.responseCode = ApiConstants.HTTP_AUTH_FAIL
          signInRecord.message = ApiConstants.AUTH_FAIL
        }
        ApiConstants.HTTP_API_FAIL -> {
          signInRecord.responseCode = ApiConstants.HTTP_API_FAIL
          signInRecord.message = ApiConstants.FAIL
        }
      }
      return signInRecord
    }else {
      return null
    }
  }
}