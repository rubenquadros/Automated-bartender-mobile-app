package com.ruben.bartender.presentation.ui.menu

/**
 * Created by Ruben Quadros on 23/10/22
 **/
sealed class MenuSideEffect {
    object NavigateToLogin : MenuSideEffect()
    data class NavigateToPayment(
        val name: String,
        val price: String
    ): MenuSideEffect()
}
