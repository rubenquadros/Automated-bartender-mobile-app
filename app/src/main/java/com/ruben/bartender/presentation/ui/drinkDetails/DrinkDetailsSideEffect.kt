package com.ruben.bartender.presentation.ui.drinkDetails

import com.ruben.bartender.presentation.ui.menu.MenuSideEffect

/**
 * Created by Ruben Quadros on 24/10/22
 **/
sealed class DrinkDetailsSideEffect {
    object DrinkIdMissing: DrinkDetailsSideEffect()
    object NavigateToLogin: DrinkDetailsSideEffect()
    data class NavigateToPayment(
        val name: String,
        val price: String
    ): DrinkDetailsSideEffect()
}
