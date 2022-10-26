package com.ruben.bartender.presentation

import okhttp3.Route

/**
 * Created by Ruben Quadros on 22/10/22
 **/
object Destination {

    object Login {
        const val Route = "login"
    }

    object SignUp: IDestination {
        const val PhoneArg = "number"
        const val Route = "signUp/{$PhoneArg}"
        override fun createRoute(vararg args: String): String = "signUp/${args[0]}"
    }

    object Home {
        const val Route = "home"
    }

    object Payment: IDestination {
        const val PriceArg = "price"
        const val DrinkNameArg = "drinkName"
        const val Route = "payment/{$PriceArg}/{$DrinkNameArg}"
        override fun createRoute(vararg args: String): String = "payment/${args[0]}/${args[1]}"
    }
}

interface IDestination {
    fun createRoute(vararg args: String): String
}