package com.ruben.bartender.presentation.ui.signup

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by Ruben Quadros on 23/10/22
 **/
@Parcelize
data class SignUpState(
    val isLoading: Boolean = false,
    val phoneNumber: String = "",
    val isFirstNameEntered: Boolean = false,
    val isLastNameEntered: Boolean = false
) : Parcelable