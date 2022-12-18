package com.ruben.bartender.presentation.ui.login

import androidx.annotation.StringRes

/**
 * Created by Ruben Quadros on 23/10/22
 **/
sealed class LoginSideEffect {
    data class ShowError(@StringRes val message: Int) : LoginSideEffect()
    object LoginSuccess : LoginSideEffect()
    data class NavigateToSignUp(val phoneNumber: String) : LoginSideEffect()
}