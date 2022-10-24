package com.ruben.bartender.presentation

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
}

interface IDestination {
    fun createRoute(vararg args: String): String
}