package com.ruben.bartender.data.repository.mapper

import com.ruben.bartender.data.remote.model.response.onBoardingResponse.LoginResponse
import com.ruben.bartender.data.remote.model.response.onBoardingResponse.SaveUserDetailsResponse
import com.ruben.bartender.data.remote.model.response.onBoardingResponse.SendOtpResponse
import com.ruben.bartender.domain.BaseRecord
import com.ruben.bartender.domain.record.ErrorRecord
import com.ruben.bartender.domain.record.LoginRecord
import com.ruben.bartender.domain.record.SendOtpErrorRecord
import com.ruben.bartender.domain.record.SendOtpRecord

/**
 * Created by Ruben Quadros on 23/10/22
 **/
fun SendOtpResponse.toSendOtpBaseRecord(): BaseRecord<SendOtpRecord, SendOtpErrorRecord> {
    return when(this) {
        is SendOtpResponse.VerificationFailed -> {
            BaseRecord.Error(
                error = SendOtpErrorRecord(message = this.message)
            )
        }
        is SendOtpResponse.VerificationComplete -> {
            BaseRecord.Success(
                body = SendOtpRecord.VerificationSuccessRecord(
                    credential = this.credential
                )
            )
        }
        is SendOtpResponse.OtpSent -> {
            BaseRecord.Success(
                body = SendOtpRecord.OtpVerificationId(
                    id = this.verificationId,
                    resendToken = this.resendToken
                )
            )
        }
    }
}

fun LoginResponse.toLoginBaseRecord(): BaseRecord<LoginRecord, ErrorRecord> {
    return when(this) {
        is LoginResponse.LoginFail -> {
            BaseRecord.Error(
                error = ErrorRecord.GenericErrorRecord(message = this.message)
            )
        }
        is LoginResponse.NewUser -> {
            BaseRecord.Success(
                body = LoginRecord.NewUser
            )
        }
        is LoginResponse.LoginSuccess -> {
            BaseRecord.Success(
                body = LoginRecord.LoginSuccess
            )
        }
    }
}

fun SaveUserDetailsResponse.toSaveUserBaseRecord(): BaseRecord<Nothing, ErrorRecord> {
    return when(this) {
        is SaveUserDetailsResponse.SaveSuccess -> {
            BaseRecord.SuccessNoBody
        }
        is SaveUserDetailsResponse.SaveFail -> {
            BaseRecord.Error(
                error = ErrorRecord.GenericErrorRecord(message = this.message)
            )
        }
    }
}