package com.ruben.bartender.presentation.ui.login

import android.os.Parcelable
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
    val phoneNumber: String = ""
): Parcelable
