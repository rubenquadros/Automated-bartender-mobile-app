package com.ruben.bartender.presentation.ui.signup

import androidx.annotation.StringRes

/**
 * Created by Ruben Quadros on 23/10/22
 **/
sealed class SignUpSideEffect {
    object SignUpSuccess : SignUpSideEffect()
    data class ShowError(
        @StringRes val message: Int,
        @StringRes val action: Int
    ) : SignUpSideEffect()
}
