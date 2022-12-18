package com.ruben.bartender.presentation.ui.login

import android.os.Parcelable
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.parcelize.Parcelize

/**
 * Created by Ruben Quadros on 23/10/22
 **/
@Parcelize
data class LoginState(
    val isLoading: Boolean = false,
    val isNumberEntered: Boolean = false,
    val isOtpEntered: Boolean = false,
    val isOtpSent: Boolean = false,
    val verificationId: String = "",
    val phoneNumber: String = "",
    val shouldShowOtpField: Boolean = false,
    val otpTimer: String = "",
    val shouldShowResendOtp: Boolean = false,
    val resendToken: PhoneAuthProvider.ForceResendingToken? = null
) : Parcelable
